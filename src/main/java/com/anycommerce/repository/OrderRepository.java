package com.anycommerce.repository;


import com.anycommerce.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 특정 유저의 주문 목록 조회
    List<Order> findByUserId(Long userId);

    // 특정 주문 조회
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
