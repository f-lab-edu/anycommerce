package com.anycommerce.repository;

import com.anycommerce.model.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermsRepository extends JpaRepository <Terms, Long> {
    List<Terms> findAllByIsRequired(boolean isRequired);

    @Query("SELECT t FROM Terms t WHERE t.version = " +
            "(SELECT MAX(t2.version) FROM Terms t2 WHERE t2.title = t.title)")
    List<Terms> findLatestTerms();

}
