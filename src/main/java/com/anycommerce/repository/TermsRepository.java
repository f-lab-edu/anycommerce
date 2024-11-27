package com.anycommerce.repository;

import com.anycommerce.model.entity.Terms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermsRepository extends JpaRepository <Terms, Long> {
    List<Terms> findAllByIsRequired(boolean isRequired);

    // 활성 상태인 약관만 조회
    @Query("SELECT t FROM Terms t WHERE t.isActive = true")
    List<Terms> findActiveTerms();

    // 최신 약관만 조회
    @Query("SELECT t FROM Terms t WHERE t.version = " +
            "(SELECT MAX(t2.version) FROM Terms t2 WHERE t2.title = t.title)")
    List<Terms> findLatestTerms();

    // 최신 버전 + 활성화된 약관만 조회
    @Query("SELECT t FROM Terms t " +
            "WHERE t.isActive = true AND " +
            "t.version = (SELECT MAX(t2.version) FROM Terms t2 WHERE t2.title = t.title AND t2.isActive = true)")
    List<Terms> findLatestActiveTerms();

}
