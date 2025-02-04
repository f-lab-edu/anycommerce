package com.anycommerce.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOption {
    private String optionSequence; // 옵션 순서
    private String optionName; // 옵션 이름 (예: "포장")
    private String optionDescription; // 옵션 설명 (예: "ㅁㅁ 포장")
    private BigDecimal originalPrice; // 옵션 원가
    private BigDecimal currentPrice; // 옵션 적용 현재 가격

}
