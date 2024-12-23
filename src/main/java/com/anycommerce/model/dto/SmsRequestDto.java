package com.anycommerce.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequestDto {

    @Schema(
            description = "SMS 발신 번호",
            example = "010-1234-5678"
    )
    private String from; // 발신 번호

    @Schema(
            description = "SMS 수신 번호",
            example = "010-8765-4321"
    )
    private String to;   // 수신 번호

    @Schema(
            description = "SMS 메시지 내용",
            example = "인증번호는 [123456]입니다."
    )
    private String text; // 메시지 내용
}
