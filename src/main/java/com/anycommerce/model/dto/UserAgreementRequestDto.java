package com.anycommerce.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAgreementRequestDto {
    @Schema(
            description = "약관의 고유 ID",
            example = "123"
    )
    private Long termsId; // 약관 ID

    @Schema(
            description = "동의 여부 (true: 동의, false: 동의 안 함)",
            example = "true"
    )
    private Boolean agreed; // 동의 여부

}
