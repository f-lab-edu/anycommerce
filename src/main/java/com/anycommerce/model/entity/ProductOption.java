package com.anycommerce.model.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ProductOption {
    private String optionSequence; // 옵션 순서
    private String productName; // 옵션 이름
    private BigDecimal originalPrice; // 원가
    private BigDecimal currentPrice; // 현재가
}
