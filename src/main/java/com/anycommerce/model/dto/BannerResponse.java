package com.anycommerce.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "배너 응답 DTO")
public class BannerResponse {
    @Schema(description = "배너 이미지 URL", example = "/images/banner1.jpg")
    private String imageUrl;

    @Schema(description = "배너 클릭 시 이동할 URL", example = "/event/newyear")
    private String linkUrl;

    @Schema(description = "배너 설명 (옵션)", example = "새해 이벤트 배너")
    private String description;

    @Schema(description = "배너 노출 시간 (초)", example = "5")
    private int displayDuration;

    @Schema(description = "배너 타입", example = "EVENT")
    private String bannerType;
}
