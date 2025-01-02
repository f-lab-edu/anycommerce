package com.anycommerce.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@Schema(description = "이미지 응답 DTO")
public class ImageResponse {

    @Schema(description = "이미지 URL", example = "/images/logo.png")
    private String imageUrl;

    @Schema(description = "이미지 너비 (픽셀)", example = "1920")
    private int width;

    @Schema(description = "이미지 높이 (픽셀)", example = "1080")
    private int height;

    @Schema(description = "이미지 설명 (옵션)", example = "로고 이미지")
    private String description;

    @Schema(description = "이미지 포맷 (확장자)", example = ".jpeg, .webp")
    private String format;

    // 확장자 추출하기, 단 Builder에 format 작성시 작동 x
    public static class ImageResponseBuilder {
        public ImageResponseBuilder format(String imageUrl) {
            int lastDotIndex  = imageUrl.lastIndexOf(".");
            if (lastDotIndex != -1 && lastDotIndex < imageUrl.length() - 1) {
                this.format = imageUrl.substring(lastDotIndex + 1);
            } else {
                this.format = "unknown";
            }
            return this;
        }
    }
}
