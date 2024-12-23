package com.anycommerce.controller;

import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.VerificationCodeResponse;
import com.anycommerce.service.VerificationCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/verification")
@Tag(name = "Verification API", description = "인증번호 인증 API")
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;

    /**
     * 인증 번호 생성 및 발송 API
     * @param phoneNumber 사용자 전화번호
     * @return 성공 메시지
     */
    @Operation(
            summary = "인증번호 생성 및 발송",
            description = "사용자의 전화번호로 인증번호를 생성하고 발송합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "인증번호 생성 및 발송 성공",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 (전화번호 형식 오류 등)")
            }
    )
    @GetMapping("/send")
    public CommonResponse<VerificationCodeResponse> sendVerificationNumber(
            @RequestParam String phoneNumber
    ){
        verificationCodeService.generateAndSendCode(phoneNumber);
        return buildResponse(phoneNumber);
    }

    /**
     * 인증 번호 검증 API
     *
     * @param phoneNumber 사용자 전화번호
     * @param randomKey   입력한 인증번호
     * @return 성공 메시지
     */
    @Operation(
            summary = "인증번호 검증",
            description = "사용자가 입력한 인증번호를 검증합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "인증 성공",
                            content = @Content(schema = @Schema(implementation = CommonResponse.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 (인증번호 형식 오류 등)"),
                    @ApiResponse(responseCode = "404", description = "인증번호 요청이 존재하지 않음"),
                    @ApiResponse(responseCode = "410", description = "인증번호가 만료되었거나 유효하지 않음")
            }
    )

    @GetMapping("/verify")
    public CommonResponse<VerificationCodeResponse> verifyCode(
            @RequestParam String phoneNumber,
            @RequestParam String randomKey
    ){
        // 핸드폰 번호 양식 재확인 (010-XXXX-XXXX)
        if (verificationCodeService.isValidPhoneNumber(phoneNumber)) {
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
        return buildResponse(phoneNumber);
    }

    /**
     * 공통 응답 생성 메서드
     * @param phoneNumber 사용자 전화번호
     * @return CommonResponse<VerificationCodeResponse>
     */
    private CommonResponse<VerificationCodeResponse> buildResponse(
            String phoneNumber
    ) {
        VerificationCodeResponse response = VerificationCodeResponse.builder()
                .phoneNumber(phoneNumber)
                .status(VerificationCodeResponse.VerificationStatus.SUCCESS)
                .message(null)
                .timestamp(LocalDateTime.now())
                .build();
        return CommonResponse.success(response);
    }


}

