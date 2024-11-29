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
    @DisplayName("인증 코드 만료 후 입력 시 실패, 새로운 인증 코드 생성 - 사용 여부 확인")
    public void testVerifyCodeWithExpiration() throws InterruptedException {
        // Given
        String phoneNumber = "01012345678";

        // Step 1: 인증 코드 생성 및 발송
        verificationCodeService.generateAndSendCode(phoneNumber);
        VerificationCode code = verificationCodeRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        String validKey = code.getRandomKey();

        // Step 2: 유효 시간을 초과하도록 시뮬레이션 (3분 초과)
        // 실제로 3분을 기다리기엔 비효율적이므로 아래와 같이 직접 시간을 변경

        code.setCreatedAt(LocalDateTime.now().minusMinutes(4));

        // Step 3: 만료된 코드로 인증 시도
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            verificationCodeService.verifyCode(phoneNumber, validKey);
        }, "유효 시간이 지난 인증 코드는 실패해야 합니다.");

        // Step 4: 새 인증 코드 생성
        verificationCodeService.generateAndSendCode(phoneNumber);
        VerificationCode newCode = verificationCodeRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        Assertions.assertNotEquals(validKey, newCode.getRandomKey(), "새로 생성된 인증 코드는 이전 코드와 달라야 합니다.");
        Assertions.assertFalse(newCode.isVerified(), "새로 생성된 인증 코드는 초기 상태여야 합니다.");
    }

}