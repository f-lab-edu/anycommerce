package com.anycommerce.model.dto;

import com.anycommerce.model.entity.Order;
import com.anycommerce.model.entity.OrderItem;
import com.anycommerce.model.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private BigDecimal discountPrice;
    private BigDecimal deliveryPrice;
    private OrderStatus orderStatus;
    private List<OrderItemResponse> orderItems;

    public static OrderResponse fromEntity(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
                .orderDate(order.getOrderDate())
                .totalPrice(order.getTotalPrice())
                .discountPrice(order.getDiscountPrice())
                .deliveryPrice(order.getDeliveryPrice())
                .orderStatus(order.getOrderStatus())
                .orderItems(order.getItems().stream()
                        .map(OrderItemResponse::fromEntity)
                        .toList())
                .build();
    }

    @Getter
    @Builder
    public static class OrderItemResponse {
        private Long productId;
        private String productName;
        private int quantity;
        private BigDecimal totalPrice;
        private BigDecimal discountPrice;

        public static OrderItemResponse fromEntity(OrderItem orderItem) {
            return OrderItemResponse.builder()
                    .productId(orderItem.getProduct().getId())
                    .productName(orderItem.getProduct().getName())
                    .quantity(orderItem.getQuantity())
                    .totalPrice(orderItem.getTotalPrice())
                    .discountPrice(orderItem.getDiscountPrice())
                    .build();
        }
    }


}
