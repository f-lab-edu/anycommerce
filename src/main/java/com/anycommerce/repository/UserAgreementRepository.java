package com.anycommerce.repository;

import com.anycommerce.model.entity.UserAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAgreementRepository extends JpaRepository<UserAgreement, Long> {
}
