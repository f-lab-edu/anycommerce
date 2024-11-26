package com.anycommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAgreementRequestDto {

    private Long termsId; // 약관 ID
    private Boolean agreed; // 동의 여부

}
