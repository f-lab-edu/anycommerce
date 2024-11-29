package com.anycommerce.service;

import com.anycommerce.config.EncryptionUtil;
import com.anycommerce.model.entity.VerificationCode;
import com.anycommerce.repository.VerificationCodeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VerificationCodeServiceTest {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Test
    @DisplayName("인증코드 생성 및 발신")
    public void testGenerateAndSendCode(){

        // Given
        String phoneNumber = "01012345678";

        // When
        verificationCodeService.generateAndSendCode(phoneNumber);

        // Then
        VerificationCode savedCode = verificationCodeRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AssertionError("Verification code not saved"));

        Assertions.assertEquals(phoneNumber, savedCode.getPhoneNumber());
        Assertions.assertNotNull(savedCode.getRandomKey());
        Assertions.assertFalse(savedCode.isVerified());
        Assertions.assertNull(savedCode.getVerifiedAt()); // 초기 상태에서는 null이어야 함
    }


    @Test
    @DisplayName("인증코드 검증 성공")
    public void testVerifyCodeSuccess() {
        // Given
        String phoneNumber = "01012345678"; // 평문 전화번호

        // 인증코드 생성 및 저장
        verificationCodeService.generateAndSendCode(phoneNumber);

        // 저장된 VerificationCode 조회 (평문 전화번호로 조회)
        VerificationCode code = verificationCodeRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AssertionError("Verification code not found"));
        String randomKey = code.getRandomKey();

        // When
        verificationCodeService.verifyCode(phoneNumber, randomKey); // 검증

        // Then
        // 검증 후 암호화한 전화번호로 다시 찾아봐야함
        VerificationCode verifiedCode = verificationCodeRepository.findByPhoneNumber(encryptionUtil.encrypt(phoneNumber))
                .orElseThrow(() -> new AssertionError("Verification code not found after verification"));

        // 검증 완료 상태 확인
        Assertions.assertTrue(verifiedCode.isVerified());
        Assertions.assertNotNull(verifiedCode.getVerifiedAt());
    }

    @Test
    @DisplayName("인증코드 인증 실패")
    public void testVerifyCodeFailure() {
        // Given
        String phoneNumber = "01012345678";
        verificationCodeService.generateAndSendCode(phoneNumber);

        // When & Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            verificationCodeService.verifyCode(phoneNumber, "wrongKey");
        });
    }

    @Test
    @DisplayName("인증코드 인증 실패 - 인증 시간 만료")
    public void testVerifyCodeExpired() {
        // Given
        String phoneNumber = "01012345678";
        verificationCodeService.generateAndSendCode(phoneNumber);

        // 인증 코드 생성 후 시간 경과 시뮬레이션
        VerificationCode code = verificationCodeRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        code.setCreatedAt(LocalDateTime.now().minusMinutes(5)); // 유효 시간(3분)을 초과한 상태로 설정
        verificationCodeRepository.save(code);

        // When & Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            verificationCodeService.verifyCode(phoneNumber, code.getRandomKey());
        }, "인증 번호가 만료되었습니다.");
    }

}