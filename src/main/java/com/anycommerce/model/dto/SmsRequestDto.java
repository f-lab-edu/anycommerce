package com.anycommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequestDto {
    private String from; // 발신 번호
    private String to;   // 수신 번호
    private String text; // 메시지 내용
}
