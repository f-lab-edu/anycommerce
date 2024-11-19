package com.anycommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAgreementRequestDto {

    private Long userId;

    /// 필수 약관 동의 여부 (클라이언트에서 각각 전송)
    private boolean requiredTerm1; // 필수 약관 1
    private boolean requiredTerm2; // 필수 약관 2
    private boolean requiredTerm3; // 필수 약관 3
    private boolean requiredTerm4; // 필수 약관 4

    // 선택 약관 1 동의 여부
    private boolean optionalTerm1;
    // 선택 약관 2 동의 여부
    private boolean optionalTerm2;

    // SMS & Email 동의 여부
    private Boolean smsConsent;
    private Boolean emailConsent;

}
