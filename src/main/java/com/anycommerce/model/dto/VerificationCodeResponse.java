package com.anycommerce.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class VerificationCodeResponse {

    @Schema(
            description = "인증 요청에 사용된 전화번호",
            example = "010-1234-5678"
    )
    private String phoneNumber; // 인증 요청 전화번호

    @Schema(
            description = "인증 상태",
            example = "SUCCESS",
            allowableValues = {"SUCCESS", "FAILED", "EXPIRED", "PENDING"}
    )
    private VerificationStatus status; // 인증 상태

    @Schema(
            description = "인증 결과 메시지",
            example = "인증이 성공적으로 완료되었습니다."
    )
    private String message; // 인증 결과 메시지

    @Schema(
            description = "응답 생성 시간",
            example = "2024-01-01T12:00:00"
    )
    private LocalDateTime timestamp; // 응답 시각

    public enum VerificationStatus {
        SUCCESS,
        FAILED,
        EXPIRED,
        PENDING
    }
}
