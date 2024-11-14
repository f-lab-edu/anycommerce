package com.anycommerce.model.dto;

import com.anycommerce.model.entity.Address;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignUpRequestDto {

    private String userId;

    private String password;

    private String confirmPassword;

    private String username;

    private String email;

    private String phoneNumber;

    @Embedded
    private Address address;

    private Boolean agreeToRequiredTerms;

    private Boolean agreeToOptionalTerms;


    // 비밀번호 일치 여부 확인 메서드
    public boolean isPasswordConfirmed() {
        return password.equals(confirmPassword);
    }
}

