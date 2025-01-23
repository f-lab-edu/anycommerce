package com.anycommerce.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    private String method; // 결제 수단 (예: 신용카드)
    private int installment; // 할부 개월 수
    private String cardInfo; // 카드 정보 (보안 고려 필요)
}
