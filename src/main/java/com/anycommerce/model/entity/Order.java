package com.anycommerce.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 주문한 사용자

    private LocalDateTime orderDate; // 주문 날짜
    private BigDecimal totalPrice; // 총 주문 금액
    private BigDecimal couponDiscountPrice; // 쿠폰 할인 금액
    private BigDecimal deliveryPrice; // 배송비
    private BigDecimal cardDiscountPrice; // 카드 할인 금액
    private int receivedPoints; // 적립된 포인트

    @Embedded
    private DeliveryAddress deliveryAddress; // 배송지 정보

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>(); // 주문 아이템 리스트

    @Embedded
    private Payment payment; // 결제 정보

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태

}
