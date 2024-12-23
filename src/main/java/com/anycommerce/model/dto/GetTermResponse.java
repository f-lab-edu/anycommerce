package com.anycommerce.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetTermResponse {

    @Schema(
            description = "약관 내용",
            example = "약관의 상세 내용이 여기에 포함됩니다."
    )
    private String term;

    public GetTermResponse(String term) {
        this.term = term;
    }
}
