package com.anycommerce.repository;

import com.anycommerce.model.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermsRepository extends JpaRepository <Terms, Long> {
    List<Terms> findAllByIsRequired(boolean isRequired);
}
