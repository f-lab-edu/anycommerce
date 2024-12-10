package com.anycommerce.controller;

import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.VerificationCodeResponse;
import com.anycommerce.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
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
        // 핸드폰 번호 확인 (010-XXXX-XXXX)
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new CustomBusinessException(ErrorCode.INVALID_PHONE_NUMBER);
        }

        verificationCodeService.generateAndSendCode(phoneNumber);
        return buildResponse(phoneNumber,"인증번호를 발송했습니다.");
    }


    @GetMapping("/verify")
    public CommonResponse<VerificationCodeResponse> verifyCode(
            @RequestParam String phoneNumber,
            @RequestParam String randomKey
    ){
        // 핸드폰 번호 양식 재확인 (010-XXXX-XXXX)
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new CustomBusinessException(ErrorCode.INVALID_PHONE_NUMBER);
        }

        // 인증 요청 존재 확인
        if (!verificationCodeService.isVerificationRequestExists(phoneNumber)) {
            throw new CustomBusinessException(ErrorCode.VERIFICATION_NOT_REQUESTED);
        }

        // 인증번호 만료 확인
        if (verificationCodeService.isVerificationCodeExpired(phoneNumber)) {
            throw new CustomBusinessException(ErrorCode.EXPIRED_VERIFICATION_CODE);
        }

        // 인증번호 일치 확인
        if (!verificationCodeService.isVerificationCodeValid(phoneNumber, randomKey)) {
            throw new CustomBusinessException(ErrorCode.INVALID_VERIFICATION_CODE);
        }

        // 모든 검증을 통과한 경우 인증 처리
        verificationCodeService.completeVerification(phoneNumber);

        // 성공 응답
        return buildResponse(
                phoneNumber,
                "인증 성공");
    }

    /**
     * 공통 응답 생성 메서드
     * @param phoneNumber 사용자 전화번호
     * @param message 응답 메시지
     * @return CommonResponse<VerificationCodeResponse>
     */
    private CommonResponse<VerificationCodeResponse> buildResponse(
            String phoneNumber,
            String message
    ) {
        VerificationCodeResponse response = VerificationCodeResponse.builder()
                .phoneNumber(phoneNumber)
                .status(VerificationCodeResponse.VerificationStatus.SUCCESS)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
        return CommonResponse.success(response);
    }

    /**
     * 핸드폰 번호 검증 메서드
     * @param phoneNumber 사용자 전화번호
     * @return boolean
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^010-\\d{4}-\\d{4}$");
    }
}

