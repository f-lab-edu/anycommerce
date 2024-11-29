package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String randomKey;

    @Column(nullable = false)
    private boolean verified;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime verifiedAt;


    /**
     * 유효 시간 체크 메서드
     * @return boolean - 유효 시간 초과 여부
     */
    public boolean isExpired() {
        // 생성된 지 3분이 지났는지 확인
        return this.createdAt.isBefore(LocalDateTime.now().minusMinutes(3));
    }

    /**
     * 검증 완료 처리 메서드
     */
    public void markAsVerified() {
        this.verified = true;
        this.verifiedAt = LocalDateTime.now();
    }

}
