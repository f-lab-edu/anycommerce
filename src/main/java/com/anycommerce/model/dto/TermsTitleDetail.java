package com.anycommerce.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TermsTitleDetail {

    @Schema(description = "약관 제목", example = "이용 약관")
    private String title;

    @Schema(description = "필수 여부", example = "true")
    private boolean isRequired;
}
