package com.anycommerce.model.dto;


import com.anycommerce.model.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Builder
@Setter
public class ProductResponse {

    @Schema(description = "상품 이름", example = "iPhone 15")
    private String name;
    @Schema(description = "상품 가격", example = "700,000")
    private BigDecimal price;
    @Schema(description = "할인 금액", example = "100,000")
    private BigDecimal discountPrice;
    @Schema(description = "할인율", example = "10(%)")
    private int discountPercentage;
    @Schema(description = "댓글 수", example = "500")
    private int comments;
    @Schema(description = "메인 상품 이미지 URL", example = "/images/main/098310.jpg")
    private String mainImageUrl;


    // 팩토리 메서드: Entity -> DTO 변환
    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .mainImageUrl(product.getMainImageUrl()) // 이미지 로직 포함
                .price(product.getPrice())
                .discountPrice(product.getDiscountPrice())
                .discountPercentage(product.getDiscountPercentage())
                .comments(product.getComments()) // 댓글 제한
                .build();
    }
}
