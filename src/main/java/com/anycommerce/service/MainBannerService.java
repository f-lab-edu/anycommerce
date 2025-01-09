package com.anycommerce.service;

import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.BannerResponse;
import com.anycommerce.model.dto.MainBannerResponse;
import com.anycommerce.model.entity.Banner;
import com.anycommerce.model.entity.BannerType;
import com.anycommerce.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainBannerService {

    private final BannerRepository bannerRepository;

    // 단일 배너 ID로 가져오기
    public BannerResponse getBannerById(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND));
        return mapToBannerResponse(banner);
    }


    public MainBannerResponse getMainBanners(){

        // 현재 시간 기준으로 활성화된 배너 가져오기
        LocalDateTime now = LocalDateTime.now();
        List<Banner> activeBanners =
                bannerRepository.findByIsActiveTrueAndStartDateTimeBeforeAndEndDateTimeAfterOrderByOrderIndexAsc(
                now, now
                );

        // DTO 변환
        List<BannerResponse> bannerResponses = activeBanners.stream()
                .map(this::mapToBannerResponse)
                .toList();

        return MainBannerResponse.builder()
                .banners(bannerResponses)
                .build();
    }


    // 특정 타입의 단일 배너 가져오기 (최신 활성화된 배너)
    public BannerResponse getBannerByType(String type) {
        Banner banner = bannerRepository.findTopByBannerTypeAndIsActiveTrueOrderByStartDateTimeDesc(
                BannerType.valueOf(type.toUpperCase()) // Enum 매핑
        ).orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND));
        return mapToBannerResponse(banner);
    }

    // 배너 엔티티를 DTO로 변환
    private BannerResponse mapToBannerResponse(Banner banner) {
        return BannerResponse.builder()
                .imageUrl(banner.getImageUrl())
                .linkUrl(banner.getLinkUrl())
                .description(banner.getDescription())
                .displayDuration(banner.getDisplayDuration())
                .bannerType(banner.getBannerType().name()) // Enum -> String
                .build();
    }

}
