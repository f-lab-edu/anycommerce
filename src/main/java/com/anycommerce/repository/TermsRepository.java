package com.anycommerce.repository;

import com.anycommerce.model.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepository extends JpaRepository <Terms, Long> {

}
