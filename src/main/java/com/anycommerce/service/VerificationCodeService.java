package com.anycommerce.service;

import com.anycommerce.config.EncryptionUtil;
import com.anycommerce.model.entity.VerificationCode;
import com.anycommerce.repository.VerificationCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final EncryptionUtil encryptionUtil;

    /**
     * 인증 코드 생성 및 발송 메서드
     *
     * @param phoneNumber 사용자 전화번호
     */
    @Transactional
    public void generateAndSendCode(String phoneNumber) {

        String randomKey = String.format("%06d", new Random().nextInt(1000000)); // 6자리 인증번호 생성

        // 기존 인증번호 조회
        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> VerificationCode.builder()
                        .phoneNumber(phoneNumber)
                        .build());


        // 인증번호 정보 업데이트
        verificationCode.setRandomKey(randomKey);
        verificationCode.setCreatedAt(LocalDateTime.now()); // 생성 시간 갱신
        verificationCode.setVerified(false); // 인증 완료 상태 초기화

        // 저장
        verificationCodeRepository.save(verificationCode);

        // TODO: SMS 발송 로직 (외부 SMS 발송 API 호출)

    }

    /**
     * 인증 코드 검증 메서드
     *
     * @param phoneNumber 사용자 전화번호
     * @param randomKey   사용자 입력 인증 코드
     *
     */
    public void verifyCode(String phoneNumber, String randomKey) {
        // TODO: DB에서 전화번호로 randomKey를 조회
        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 번호로 인증 요청이 없습니다."));

            // 유효 시간 초과 확인
            if (verificationCode.isExpired()) {
                throw new IllegalArgumentException("인증 번호가 만료되었습니다.");
            }

            // 인증 번호 일치 확인
            if (!verificationCode.getRandomKey().equals(randomKey)) {
                throw new IllegalArgumentException("인증 번호가 일치하지 않습니다.");
            }

            // 인증 성공: 전화번호 암호화 후 저장
            String encryptedPhoneNumber = encryptionUtil.encrypt(phoneNumber);
            verificationCode.setPhoneNumber(encryptedPhoneNumber);
            verificationCode.setVerified(true);
            verificationCodeRepository.save(verificationCode);
        }
}

