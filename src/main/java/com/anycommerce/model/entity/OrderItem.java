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
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // 주문과의 연관 관계

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 상품 정보

    private int quantity; // 수량
    private BigDecimal totalPrice; // 총 가격
    private BigDecimal discountPrice; // 할인 금액

    @ElementCollection
    private List<ProductOption> productOptions = new ArrayList<>(); // 상품 옵션

    public static OrderItem fromCartItem(CartItem cartItem) {
        return OrderItem.builder()
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .discountPrice(cartItem.getDiscountPrice())
                .productOptions(cartItem.getProductOptions())
                .build();
    }

}
