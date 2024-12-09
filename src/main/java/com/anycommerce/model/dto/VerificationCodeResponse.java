package com.anycommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class VerificationCodeResponse {
    private String phoneNumber; // 인증 요청 전화번호 (마스킹 가능)
    private String status; // 인증 상태 ("SUCCESS" or "FAILED")
    private String message; // 인증 결과 메시지
    private LocalDateTime timestamp; // 응답 시각
}
