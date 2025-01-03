package com.anycommerce.controller;

import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.ImageResponse;
import com.anycommerce.model.dto.JumbotronResponse;
import com.anycommerce.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
@Tag(name = "Image API", description = "이미지 API")
public class ImageController {

    private final ImageService imageService;

    /**
     * 로고 이미지 반환 API
     *
     * @return 로고 이미지 정보
     */
    @GetMapping("/logo")
    @Operation(
            summary = "로고 이미지 가져오기",
            description = "애니커머스 로고 이미지를 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "로고 이미지 반환 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))
            )
    })
    public CommonResponse<ImageResponse> getLogoImage() {
        ImageResponse logoImage = imageService.getLogoImage();
        return CommonResponse.success(logoImage);
    }

    /**
     * 점보트론 이미지 리스트 반환 API
     *
     * @return 점보트론 이미지 리스트
     */
    @GetMapping("/jumbotron")
    @Operation(
            summary = "점보트론 이미지 리스트 가져오기",
            description = "점보트론에 사용되는 이미지 리스트를 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "점보트론 이미지 리스트 반환 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))
            )
    })
    public CommonResponse<JumbotronResponse> getJumbotronImages(){
        List<ImageResponse> images = imageService.getJumbotronImages();
        JumbotronResponse response = JumbotronResponse.builder()
                .imageList(images)
                .build();
        return CommonResponse.success(response);
    }

}
