package com.anycommerce.model.entity;

public enum OrderStatus {
    PENDING, // 결제 대기 중
    COMPLETE, // 결제 완료
    CANCELLED, // 주문 취소
    FAILED // 결제 실패
}
