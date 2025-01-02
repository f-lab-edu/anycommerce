package com.anycommerce.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "점보트론 이미지 응답 DTO")
public class JumbotronResponse {

    @Schema(description = "점보트론 이미지 리스트")
    private List<ImageResponse> imageList;
}
