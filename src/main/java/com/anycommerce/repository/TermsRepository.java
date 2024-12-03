package com.anycommerce.repository;

import com.anycommerce.model.entity.Terms;
import com.anycommerce.model.entity.TermsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermsRepository extends JpaRepository <Terms, TermsId> {

    // 필수 약관만 조회
    List<Terms> findAllByIsRequired(boolean isRequired);

    // 최신 버전 + 활성화된 약관 + 필수 약관 조회
    @Query("SELECT t FROM Terms t " +
            "WHERE t.isActive = true AND t.isRequired = true AND " +
            "t.id.version = (SELECT MAX(t2.id.version) FROM Terms t2 WHERE t2.id.title = t.id.title)")
    List<Terms> findLatestRequiredTerms();


    // 최신 버전 + 활성화된 약관만 조회
    @Query("SELECT t FROM Terms t " +
            "WHERE t.isActive = true AND " +
            "t.id.version = (SELECT MAX(t2.id.version) FROM Terms t2 WHERE t2.id.title = t.id.title AND t2.isActive = true)")
    List<Terms> findLatestActiveTerms();

}
