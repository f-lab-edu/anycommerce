package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "agreements")
public class UserAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean requiredTerm;

    @Column(nullable = false)
    private boolean optionalTerm1;

    @Column(nullable = false)
    private boolean optionalTerm2;

    @Column(nullable = true)
    private Boolean smsConsent; // SMS 동의 여부 (마케팅 약관 동의 시만 설정)

    @Column(nullable = true)
    private Boolean emailConsent; // Email 동의 여부 (마케팅 약관 동의 시만 설정)

    @Column(nullable = false)
    private LocalDateTime agreedAt;
}
