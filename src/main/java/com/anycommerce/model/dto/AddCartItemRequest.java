package com.anycommerce.model.dto;

import com.anycommerce.model.entity.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemRequest {
    private Long productId; // 상품 ID
    private int quantity; // 추가할 수량
    private List<ProductOptionRequest> options; // 상품 옵션 리스트


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOptionRequest {
        private String optionName; // 옵션 이름
        private String optionValue; // 옵션 값
    }

}
