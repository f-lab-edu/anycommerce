package com.anycommerce.model.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class TermsTitleResponse {

    @ArraySchema(
            schema = @Schema(description = "약관 목록", implementation = TermsTitleDetail.class),
            arraySchema = @Schema(description = "약관 리스트", example = "[{\"title\":\"이용 약관\",\"isRequired\":true}]")
    )
    private List<TermsTitleDetail> termsList;

}
