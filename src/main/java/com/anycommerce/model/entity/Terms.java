package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "terms")
public class Terms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 약관 내용
    @Column(nullable = false)
    private String content;

    // 약관 버전
    @Column(nullable = false)
    private String version;

    // 필수 여부
    @Column(nullable = false)
    private boolean isRequired;
}
