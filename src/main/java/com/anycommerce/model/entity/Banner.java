package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl; // 배너 이미지 URL

    @Column(nullable = false)
    private String linkUrl; // 배너 클릭 시 이동할 URL

    private String description; // 배너 설명 (예: 이벤트 이름)

    private boolean isActive; // 배너 활성화 여부

    private int orderIndex; // 배너 표시 순서

    private int displayDuration; // 배너 노출 시간 (초), 고정 배너일 경우 0

    @Enumerated(EnumType.STRING)
    private TargetGender targetGender; // 배너 노출 대상 성별 (남성, 여성, 전체)

    private int targetMinAge; // 최소 연령 제한 (예: 0이면 제한 없음)

    private int targetMaxAge; // 최대 연령 제한 (예: 100이면 제한 없음)

    @Enumerated(EnumType.STRING)
    private BannerType bannerType; // 배너 타입 (화장품, 의류, 식품 등)

    private LocalDateTime startDateTime; // 배너 시작 노출 시간

    private LocalDateTime endDateTime; // 배너 종료 노출 시간

}
