package com.anycommerce.controller;

import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.ImageResponse;
import com.anycommerce.model.dto.JumbotronResponse;
import com.anycommerce.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    // 로고 이미지 반환
    @GetMapping("/logo")
    public CommonResponse<ImageResponse> getLogoImage() {
        ImageResponse logoImage = imageService.getLogoImage();
        return CommonResponse.success(logoImage);
    }

    // 점보트론 이미지 리스트 반환
    @GetMapping("/jumbotron")
    public CommonResponse<JumbotronResponse> getJumbotronImages(){
        List<ImageResponse> images = imageService.getJumbotronImages();
        JumbotronResponse response = JumbotronResponse.builder()
                .imageList(images)
                .build();
        return CommonResponse.success(response);
    }

}
