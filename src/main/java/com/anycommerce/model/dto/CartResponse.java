package com.anycommerce.model.dto;

import com.anycommerce.model.entity.Cart;
import com.anycommerce.model.entity.CartItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private Long cartId; // 장바구니 ID
    private Long userId; // 사용자 ID
    private int totalItems; // 장바구니의 총 상품 수량
    private BigDecimal totalPrice; // 총 금액
    private BigDecimal discountPrice; // 할인 금액
    private BigDecimal deliveryPrice; // 배송비
    private DeliveryAddress deliveryAddress; // 배송지 정보
    private List<CartItemResponse> items; // 장바구니 아이템 목록

    // 엔티티를 DTO로 변환하는 메서드
    public static CartResponse fromEntity(Cart cart) {
        return CartResponse.builder()
                .userId(cart.getUserId())
                .totalItems(cart.getItems().size())
                .totalPrice(cart.getTotalPrice())
                .discountPrice(cart.getDiscountPrice())
                .deliveryPrice(cart.getDeliveryPrice())
                .deliveryAddress(DeliveryAddress.fromEntity(cart.getDeliveryAddress()))
                .items(cart.getItems().stream()
                        .map(CartItemResponse::fromEntity)
                        .toList())
                .build();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItemResponse {
        private Long itemId; // CartItem ID
        private Long productId; // 상품 ID
        private String productName; // 상품명
        private int quantity; // 수량
        private BigDecimal totalPrice; // 총 금액
        private BigDecimal discountAmount; // 할인 금액
        private List<ProductOptionResponse> productOptions; // 상품 옵션

        // CartItem 엔티티를 DTO로 변환하는 메서드
        public static CartItemResponse fromEntity(CartItem cartItem) {
            return CartItemResponse.builder()
                    .itemId(cartItem.getId())
                    .productId(cartItem.getProduct().getId())
                    .productName(cartItem.getProduct().getName())
                    .quantity(cartItem.getQuantity())
                    .totalPrice(cartItem.getTotalPrice())
                    .discountAmount(cartItem.getDiscountPrice())
                    .productOptions(cartItem.getProductOptions().stream()
                            .map(option -> ProductOptionResponse.builder()
                                    .optionName(option.getOptionName())
                                    .optionDescription(option.getOptionDescription())
                                    .additionalPrice(option.getOriginalPrice())
                                    .build())
                            .toList())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOptionResponse {
        private String optionName; // 옵션 이름
        private String optionDescription; // 옵션 설명
        private BigDecimal additionalPrice; // 추가 금액
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeliveryAddress {
        private String zipcode; // 우편번호
        private String streetAddress; // 도로명 주소
        private String detailAddress; // 상세 주소

        // 엔티티에서 배송 주소를 변환하는 메서드 (필요시 구현)
        public static DeliveryAddress fromEntity(com.anycommerce.model.entity.DeliveryAddress address) {
            if (address == null) {
                return null;
            }
            return DeliveryAddress.builder()
                    .zipcode(address.getZipcode())
                    .streetAddress(address.getStreetAddress())
                    .detailAddress(address.getDetailAddress())
                    .build();
        }
    }
}
