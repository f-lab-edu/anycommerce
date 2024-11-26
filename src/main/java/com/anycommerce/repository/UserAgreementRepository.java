package com.anycommerce.repository;

import com.anycommerce.model.entity.Terms;
import com.anycommerce.model.entity.User;
import com.anycommerce.model.entity.UserAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAgreementRepository extends JpaRepository<UserAgreement, Long> {

    List<UserAgreement> findAllByUserAndAgreed(User user, boolean agreed);

    Optional<UserAgreement> findByUserAndTerms(User user, Terms terms);

}
