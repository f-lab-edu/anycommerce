package com.anycommerce.service;


import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.OrderResponse;
import com.anycommerce.model.entity.*;
import com.anycommerce.repository.CartRepository;
import com.anycommerce.repository.OrderRepository;
import com.anycommerce.repository.ProductRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Transactional
    public OrderResponse placeOrder(Long userId) {

        // 장바구니 조회
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND));

        if (cart.getItems().isEmpty()) {
            throw new CustomBusinessException(ErrorCode.NOT_FOUND);
        }

        // 주문 생성
        Order order = Order.builder()
                .totalPrice(cart.getTotalPrice())
                .discountPrice(cart.getDiscountPrice())
                .deliveryPrice(cart.getDeliveryPrice())
                .deliveryAddress(cart.getDeliveryAddress())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .items(cart.getItems().stream().map(this::convertToOrderItem).toList())
                .build();

        // OrderItem과 관계 설정
        order.getItems().forEach(item -> item.setOrder(order));

        // 주문 저장
        orderRepository.save(order);

        // 장바구니 비우기 (연관된 CartItem 제거)
        cart.getItems().clear();
        cartRepository.save(cart);

        return OrderResponse.fromEntity(order);

    }

    private OrderItem convertToOrderItem(CartItem cartItem){
        return OrderItem.builder()
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .discountPrice(cartItem.getTotalPrice())
                .productOptions(cartItem.getProductOptions())
                .build();
    }

    // 주문 상세보기
    @Transactional(readOnly = true)
    public OrderResponse getOrderDetail(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND));

        return OrderResponse.fromEntity(order);
    }

    // 주문 취소하기
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND));

        if(order.getOrderStatus() != OrderStatus.PENDING) {
            throw new CustomBusinessException(ErrorCode.ORDER_CANNOT_BE_CANCELLED);
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    // 주문 상태 업데이트하기
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus newStatus){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND));

        order.setOrderStatus(newStatus);
        orderRepository.save(order);
    }


}
