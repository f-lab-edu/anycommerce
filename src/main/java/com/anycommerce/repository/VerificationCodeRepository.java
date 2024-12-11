package com.anycommerce.repository;

import com.anycommerce.model.entity.VerificationCode;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByPhoneNumber(String phoneNumber);

    @Modifying
    @Transactional
    @Query("DELETE FROM VerificationCode v WHERE v.phoneNumber = :phoneNumber")
    void deleteByPhoneNumber(String phoneNumber);
}
