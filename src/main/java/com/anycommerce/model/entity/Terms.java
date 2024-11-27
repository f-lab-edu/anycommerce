package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Terms extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 약관 제목
    @Column(nullable = false)
    private String title;

    // 약관 내용
    @Column(nullable = false)
    private String content;

    // 약관 버전
    @Column(nullable = false)
    private String version;

    // 필수 여부
    @Column(nullable = false)
    private boolean isRequired;

    // 활성/비활성 상태 추가
    @Column(nullable = false)
    private boolean isActive = true; // 기본값: 활성 상태
}
