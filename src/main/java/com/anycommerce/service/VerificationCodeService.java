package com.anycommerce.service;

import com.anycommerce.model.entity.VerificationCode;
import com.anycommerce.repository.VerificationCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@RequiredArgsConstructor
@Service
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * 랜덤 키 생성 메서드
     * 6자리 숫자로 구성된 랜덤 키를 생성합니다.
     *
     * @return 6자리 랜덤 키
     */
    public String generateRandomKey() {
        int randomKey = secureRandom.nextInt(1000000); // 0 ~ 999999
        return String.format("%06d", randomKey); // 6자리로 고정
    }

    /**
     * 인증 코드 생성 및 발송 메서드
     *
     * @param phoneNumber 사용자 전화번호
     */
    @Transactional
    public void generateAndSendVerificationCode(String phoneNumber) {
        String randomKey = generateRandomKey();

        // 기존 데이터 삭제 후 새로 생성 (중복으로 받을 때 고려)
        verificationCodeRepository.findByPhoneNumber(phoneNumber).ifPresent(verificationCodeRepository::delete);

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setPhoneNumber(phoneNumber);
        verificationCode.setRandomKey(randomKey);
        verificationCode.setCreatedAt(System.currentTimeMillis());

        // randomKey를 DB에 저장 (phoneNumber와 함께 저장)
        verificationCodeRepository.save(verificationCode);

        // TODO: SMS 발송 로직 (외부 SMS 발송 API 호출)

    }

    /**
     * 인증 코드 검증 메서드
     *
     * @param phoneNumber 사용자 전화번호
     * @param randomKey   사용자 입력 랜덤 키
     * @return 검증 결과 메시지
     */
    public boolean verifyCode(String phoneNumber, String randomKey) {
        // TODO: DB에서 전화번호로 randomKey를 조회
        VerificationCode storedCode = verificationCodeRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("핸드폰 번호가 없습니다."));

            return storedCode.getRandomKey().equals(randomKey);
        }



    }

