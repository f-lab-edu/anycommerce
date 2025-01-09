package com.anycommerce.controller;

import com.anycommerce.model.dto.BannerResponse;
import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.MainBannerResponse;
import com.anycommerce.service.MainBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/main-banner")
@RequiredArgsConstructor
public class MainBannerController {

    private final MainBannerService mainBannerService;

    // 메인 롤링 배너 가져오기
    @GetMapping
    public CommonResponse<MainBannerResponse> getMainBanners(){
        MainBannerResponse response = mainBannerService.getMainBanners();
        return CommonResponse.success(response);
    }

    // 단일 광고 배너 가져오기
    @GetMapping("/{id}")
    public CommonResponse<BannerResponse> getBannerById(@PathVariable Long id) {
        BannerResponse response = mainBannerService.getBannerById(id);
        return CommonResponse.success(response);
    }

    // 특정 타입의 단일 광고 배너 가져오기 (예: 광고 배너)
    @GetMapping("/type/{type}")
    public CommonResponse<BannerResponse> getBannerByType(@PathVariable String type) {
        BannerResponse response = mainBannerService.getBannerByType(type);
        return CommonResponse.success(response);
    }

}
