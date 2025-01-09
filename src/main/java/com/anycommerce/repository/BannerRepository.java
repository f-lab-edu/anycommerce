package com.anycommerce.repository;

import com.anycommerce.model.entity.Banner;
import com.anycommerce.model.entity.BannerType;
import com.anycommerce.model.entity.TargetGender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    // 현재 활성화된 배너를 표시 순서대로 가져오기
    List<Banner> findByIsActiveTrueAndStartDateTimeBeforeAndEndDateTimeAfterOrderByOrderIndexAsc(
            LocalDateTime now1, LocalDateTime now2
    );

    // 특정 타겟(연령, 성별)에 맞는 배너만 가져오기
    List<Banner> findByIsActiveTrueAndTargetGenderAndTargetMinAgeLessThanEqualAndTargetMaxAgeGreaterThanEqualOrderByOrderIndexAsc(
            TargetGender gender, int minAge, int maxAge
    );

    // 특정 배너 타입의 최신 활성화된 배너 가져오기
    Optional<Banner> findTopByBannerTypeAndIsActiveTrueOrderByStartDateTimeDesc(BannerType bannerType);
}
