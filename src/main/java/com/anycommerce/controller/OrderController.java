package com.anycommerce.controller;

import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.OrderResponse;
import com.anycommerce.model.entity.OrderStatus;
import com.anycommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 생성
     */
    @PostMapping("/placeOrder")
    public CommonResponse<OrderResponse> placeOrder(@RequestParam Long userId) {
        return CommonResponse.success(orderService.placeOrder(userId));
    }

    /**
     * 주문 상세 조회
     */
    @GetMapping("/{orderId}")
    public CommonResponse<OrderResponse> getOrderDeatil(@PathVariable Long orderId){
        return CommonResponse.success(orderService.getOrderDetail(orderId));
    }

    /**
     * 주문 취소
     */
    @PostMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
    }

    /**
     * 주문 상태 업데이트
     */
    @PostMapping("/{orderId}/status")
    public void updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus newStatus){
        orderService.updateOrderStatus(orderId, newStatus);
    }

}
