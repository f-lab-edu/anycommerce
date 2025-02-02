package com.anycommerce.model.dto;


import com.anycommerce.model.entity.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class ProductDetailResponse {
    private Long id; // 상품 ID
    private String name; // 상품명
    private String description; // 상품 설명
    private BigDecimal price; // 원가
    private BigDecimal discountPrice; // 할인가
    private int discountPercentage; // 할인율
    private int stockQuantity; // 재고 수량
    private String mainImageUrl; // 대표 이미지 URL
    private String brandName; // 브랜드명
    private String deliveryType; // 배송 유형 (예: 샛별배송)
    private String deliveryInfo; // 배송 정보
    private String packingType; // 포장 타입
    private String extraInfo; // 추가 정보

    private List<Image> images; // 이미지 리스트
    private List<ProductOptionResponse> options; // 옵션 리스트

    @Getter
    @Setter
    @Builder
    public static class ProductOptionResponse {
        private String optionSequence; // 옵션 순서
        private String productName; // 옵션명
        private BigDecimal originalPrice; // 옵션의 원가
        private BigDecimal currentPrice; // 할인가 적용된 옵션 최종가격
    }
}
