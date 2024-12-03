package com.anycommerce.repository;

import com.anycommerce.model.entity.Terms;
import com.anycommerce.model.entity.User;
import com.anycommerce.model.entity.UserAgreement;
import com.anycommerce.model.entity.UserAgreementId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAgreementRepository extends JpaRepository<UserAgreement, UserAgreementId> {

    List<UserAgreement> findAllByIdUserAndAgreed(User user, boolean agreed);

    Optional<UserAgreement> findByIdUserAndIdTerms(User user, Terms terms);

}
