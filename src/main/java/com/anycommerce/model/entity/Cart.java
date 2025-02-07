package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    private Long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();

    private BigDecimal totalPrice;
    private BigDecimal discountPrice;
    private BigDecimal deliveryPrice;

    @Embedded
    private DeliveryAddress deliveryAddress;

    // 장바구니 상품 추가
    public void addItem(CartItem item) {
        item.setCart(this);
        items.add(item);
    }

    // 장바구니에서 아이템 제거
    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }


    // 수량 조절 메서드 추가
    public void updateItemQuantity(CartItem item, int quantity) {
        if (quantity <= 0) {
            // 수량이 0 이하라면 장바구니에서 제거
            removeItem(item);
        } else {
            // 수량을 업데이트하고 금액 재계산
            item.setQuantity(quantity);
            BigDecimal totalPrice = item.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
            item.setTotalPrice(totalPrice);

        }
    }
}
