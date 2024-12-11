package com.anycommerce.service;

import com.anycommerce.config.EncryptionUtil;
import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.VerificationCodeResponse;
import com.anycommerce.model.entity.Blacklist;
import com.anycommerce.model.entity.VerificationCode;
import com.anycommerce.repository.BlacklistRepository;
import com.anycommerce.repository.VerificationCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Slf4j
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final EncryptionUtil encryptionUtil;
    private final BlacklistRepository blacklistRepository;
    private final VerificationCode verificationCode;

    // 인증 요청 여부 확인
    public boolean isVerificationRequestExists(String phoneNumber) {
        return verificationCodeRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    // 인증번호 만료 확인
    public boolean isVerificationCodeExpired(String phoneNumber) {
        return verificationCodeRepository.findByPhoneNumber(phoneNumber)
                .map(VerificationCode::isExpired)
                .orElse(true); // 인증 요청이 없으면 만료된 것으로 처리
    }

    // 인증번호 일치 확인
    public boolean isVerificationCodeValid(String phoneNumber, String randomKey) {
        return verificationCodeRepository.findByPhoneNumber(phoneNumber)
                .map(code -> Objects.equals(code.getRandomKey(), randomKey))
                .orElse(false); // 인증 요청이 없으면 false 반환
    }

    /**
     * 전화번호 형식 검증 메서드
     *
     * @param phoneNumber 사용자 전화번호
     * @return true: 유효, false: 유효하지 않음
     */
    public boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^010-\\d{4}-\\d{4}$");
    }

    /**
     * 인증 코드 생성 및 발송 메서드
     *
     * @param phoneNumber 사용자 전화번호
     */
    @Transactional
    public void generateAndSendCode(String phoneNumber) {

        // 전화번호 형식 검증
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new CustomBusinessException(ErrorCode.INVALID_PHONE_NUMBER);
        }

        // 블랙리스트 및 발송 가능 여부 확인
        validateBlacklistAndRequestLimit(phoneNumber);

        // 기존 인증번호 조회 또는 새로 생성
        VerificationCode verificationCode = findOrCreateVerificationCode(phoneNumber);

        // 발송 가능 여부 확인
        if (!verificationCode.canSendCode()) {
            throw new CustomBusinessException(ErrorCode.TOO_MANY_REQUESTS);
        }

        // 최근 생성된 코드가 만료되지 않은 경우, 제한 메시지 표시
        if (!verificationCode.isExpired() && verificationCode.getAttempts() > 0) {
            throw new CustomBusinessException(ErrorCode.TOO_MANY_REQUESTS);
        }

        // 6자리 인증번호 생성
        String randomKey = String.format("%06d", new Random().nextInt(1000000)); // 6자리 인증번호 생성

        
        // 인증번호 정보 업데이트
        verificationCode.setRandomKey(randomKey);
        verificationCode.setCreatedAt(LocalDateTime.now()); // 생성 시간 갱신
        verificationCode.setStatus(VerificationCodeResponse.VerificationStatus.PENDING);
        verificationCode.setAttempts(verificationCode.getAttempts() + 1);

        verificationCodeRepository.save(verificationCode);

        log.info("인증번호 발송: {} -> {}", phoneNumber, randomKey);

        // TODO: SMS 발송 로직 (외부 SMS 발송 API 호출)

    }


    /**
     * 인증 코드 검증 메서드
     *
     * @param phoneNumber 사용자 전화번호
     * @param randomKey   사용자 입력 인증 코드
     */
    @Transactional
    public void verifyCode(String phoneNumber, String randomKey) {

        // 블랙리스트 체크

        if (blacklistRepository.existsByPhoneNumber(phoneNumber)) {
            throw new CustomBusinessException(ErrorCode.BLACKLISTED_PHONE_NUMBER);
        }

        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.VERIFICATION_NOT_REQUESTED));

        // 1. 최대 시도 초과 여부 확인
        if (verificationCode.hasExceededMaxAttempts()) {
            updateVerificationStatus(verificationCode, VerificationCodeResponse.VerificationStatus.EXPIRED);
            throw new CustomBusinessException(ErrorCode.TOO_MANY_VERIFICATION_ATTEMPTS);
        }

        // 2. 유효 시간 초과 확인
        if (verificationCode.isExpired()) {
            updateVerificationStatus(verificationCode, VerificationCodeResponse.VerificationStatus.EXPIRED);
            throw new CustomBusinessException(ErrorCode.EXPIRED_VERIFICATION_CODE);
        }

        // 3. 인증번호 일치 여부 확인
        if (!Objects.equals(verificationCode.getRandomKey(), randomKey)) {
            updateVerificationStatus(verificationCode, VerificationCodeResponse.VerificationStatus.FAILED);
            throw new CustomBusinessException(ErrorCode.INVALID_VERIFICATION_CODE);
        }

        // 4. 성공 시 처리
        completeVerification(phoneNumber);
    }


    /**
     * 인증 완료 처리
     *
     * @param phoneNumber 사용자 전화번호
     */
    @Transactional
    public void completeVerification(String phoneNumber) {
        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.VERIFICATION_NOT_REQUESTED));

        // 이미 성공 상태라면 추가 처리 없이 리턴
        if (verificationCode.getStatus() == VerificationCodeResponse.VerificationStatus.SUCCESS) {
            log.info("해당 번호는 이미 인증 성공 상태입니다: {}", phoneNumber);
            return;
        }

        updateVerificationStatus(verificationCode, VerificationCodeResponse.VerificationStatus.SUCCESS);
        log.info("인증 성공: {}", phoneNumber);
    }

    /**
     * 신규 인증번호 생성 시 로직
     *
     * @param phoneNumber 사용자 전화번호
     */

    private VerificationCode findOrCreateVerificationCode(String phoneNumber) {
        return verificationCodeRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> VerificationCode.builder()
                        .phoneNumber(phoneNumber)
                        .status(VerificationCodeResponse.VerificationStatus.PENDING)
                        .attempts(0)
                        .build());
    }

    /**
     * 상태 업데이트
     *
     * @param verificationCode 인증번호 객체
     * @param status 인증 상태
     */

    private void updateVerificationStatus(VerificationCode verificationCode, VerificationCodeResponse.VerificationStatus status) {
        if (verificationCode.getStatus() == status) {
            return; // 상태가 이미 같은 경우 업데이트하지 않음
        }
        verificationCode.setStatus(status);
        verificationCodeRepository.save(verificationCode);
        log.info("Verification status updated for phone number {}: {}", verificationCode.getPhoneNumber(), status);
    }

    /**
     * 인증이 완료된 전화번호를 암호화하여 반환
     *
     * @param phoneNumber 인증된 전화번호
     * @return 암호화된 전화번호
     */
    public String encryptVerifiedPhoneNumber(String phoneNumber) {
        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.VERIFICATION_NOT_REQUESTED));

        // 인증 상태 확인
        if (!verificationCode.getStatus().equals(VerificationCodeResponse.VerificationStatus.SUCCESS)) {
            throw new CustomBusinessException(ErrorCode.INVALID_VERIFICATION_CODE);
        }

        // 전화번호 암호화 후 반환
        return encryptionUtil.encrypt(phoneNumber);
    }


    /**
     * 블랙리스트와 인증번호 발송 제한 로직
     *
     * @param phoneNumber 전화번호
     *
     */
    private void validateBlacklistAndRequestLimit(String phoneNumber) {
        if (blacklistRepository.existsByPhoneNumber(phoneNumber)) {
            throw new CustomBusinessException(ErrorCode.BLACKLISTED_PHONE_NUMBER);
        }

        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> VerificationCode.builder()
                        .phoneNumber(phoneNumber)
                        .status(VerificationCodeResponse.VerificationStatus.PENDING)
                        .attempts(0)
                        .build());

        if (!verificationCode.canSendCode()) {
            throw new CustomBusinessException(ErrorCode.TOO_MANY_REQUESTS);
        }

        verificationCodeRepository.save(verificationCode);
    }


    @Transactional
    public void blockPhoneNumber(String phoneNumber) {
        blacklistRepository.save(new Blacklist(phoneNumber));
        log.warn("전화번호가 블랙리스트에 추가되었습니다: {}", phoneNumber);
    }


}

