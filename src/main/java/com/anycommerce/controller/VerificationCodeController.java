package com.anycommerce.controller;

import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.VerificationCodeResponse;
import com.anycommerce.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/verification")
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;

    /**
     * 인증 번호 생성 및 발송 API
     * @param phoneNumber 사용자 전화번호
     * @return 성공 메시지
     */
    @GetMapping("/send")
    public CommonResponse<VerificationCodeResponse> sendVerificationNumber(
            @RequestParam String phoneNumber

    ){
        // 전화번호 검증 로직 (Validation Check)
        if (!phoneNumber.matches("^010-\\d{4}-\\d{4}$")) {
            return CommonResponse.fromError(ErrorCode.INVALID_PHONE_NUMBER, null);
        }

        // 인증번호 생성 및 발송
        verificationCodeService.generateAndSendCode(phoneNumber);

        VerificationCodeResponse response = VerificationCodeResponse.builder()
                .phoneNumber(phoneNumber) // 마스킹 처리 필요 시 Utility 사용
                .status("SUCCESS")
                .message("인증번호를 발송했습니다.")
                .timestamp(LocalDateTime.now())
                .build();

        // 성공 응답
        return CommonResponse.success(response);

    }


    @GetMapping("/verify")
    public CommonResponse<VerificationCodeResponse> verifyCode(
            @RequestParam String phoneNumber,
            @RequestParam String randomKey
    ){
        // 인증 요청 존재 확인
        if (!verificationCodeService.isVerificationRequestExists(phoneNumber)) {
            return CommonResponse.fromError(ErrorCode.VERIFICATION_NOT_REQUESTED, null);
        }

        // 인증번호 만료 확인
        if (verificationCodeService.isVerificationCodeExpired(phoneNumber)) {
            return CommonResponse.fromError(ErrorCode.EXPIRED_VERIFICATION_CODE, null);
        }

        // 인증번호 일치 확인
        if (!verificationCodeService.isVerificationCodeValid(phoneNumber, randomKey)) {
            return CommonResponse.fromError(ErrorCode.INVALID_VERIFICATION_CODE, null);
        }

        // 모든 검증을 통과한 경우 인증 처리
        verificationCodeService.completeVerification(phoneNumber);

        VerificationCodeResponse response = VerificationCodeResponse.builder()
                .phoneNumber(phoneNumber)
                .status("SUCCESS")
                .message("인증 성공")
                .timestamp(LocalDateTime.now())
                .build();

        // 성공 응답
        return CommonResponse.success(response);
    }

}

