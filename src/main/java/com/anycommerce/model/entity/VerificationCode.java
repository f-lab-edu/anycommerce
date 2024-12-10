package com.anycommerce.model.entity;

import com.anycommerce.model.dto.VerificationCodeResponse;
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

    // 상태 필드 추가
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationCodeResponse.VerificationStatus status;

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
        this.status = VerificationCodeResponse.VerificationStatus.SUCCESS;
        this.verifiedAt = LocalDateTime.now();
    }

    /**
     * 검증 실패 처리 메서드
     */
    public void markAsFailed() {
        this.status = VerificationCodeResponse.VerificationStatus.FAILED;
        this.verifiedAt = LocalDateTime.now();
    }

    /**
     * 만료 처리 메서드
     */
    public void markAsExpired() {
        this.status = VerificationCodeResponse.VerificationStatus.EXPIRED;
        this.verifiedAt = LocalDateTime.now();
    }

    /**
     * 엔티티가 데이터베이스에 처음 저장되기 전에 기본 상태를 설정
     */
    @PrePersist
    private void prePersist() {
        if (this.status == null) {
            this.status = VerificationCodeResponse.VerificationStatus.PENDING;
        }
    }

}
