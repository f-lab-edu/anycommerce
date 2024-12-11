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

    @Column(nullable = false)
    private int attempts; // 틀린 횟수 추가

    @Column(nullable = true)
    private LocalDateTime lastSentAt;

    // 최대 시도 제한
    private static final int MAX_ATTEMPTS = 5;

    /**
     * 인증 번호 틀린 횟수 증가
     */
    public void incrementAttempts() {
        this.attempts++;
    }

    /**
     * 틀린 횟수 제한 여부 확인
     */
    public boolean hasExceededMaxAttempts() {
        return this.attempts >= MAX_ATTEMPTS;
    }

    /**
     * 유효 시간 체크 메서드
     * @return boolean - 유효 시간 초과 여부
     */
    public boolean isExpired() {
        // 생성된 지 3분이 지났는지 확인
        return this.createdAt.isBefore(LocalDateTime.now().minusMinutes(3));
    }

    // 발송 가능 여부 확인
    public boolean canSendCode() {
        if (attempts == 4 && lastSentAt != null) {
            return lastSentAt.isBefore(LocalDateTime.now().minusSeconds(90)); // 1분 30초 제한
        }
        if (attempts >= 5 && lastSentAt != null) {
            return lastSentAt.isBefore(LocalDateTime.now().minusMinutes(2)); // 2분 제한
        }
        return true; // 첫 3회는 제한 없음
    }

    /**
     * 검증 완료 처리 메서드
     */
    public void markAsVerified() {
        this.status = VerificationCodeResponse.VerificationStatus.SUCCESS;
        this.verifiedAt = LocalDateTime.now();
        this.attempts = 0;
    }

    /**
     * 검증 실패 처리 메서드
     */
    public void markAsFailed() {
        this.status = VerificationCodeResponse.VerificationStatus.FAILED;
        incrementAttempts();
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
