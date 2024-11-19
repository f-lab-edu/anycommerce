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

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,20}$",
            message = "비밀번호는 8자 이상, 20자 이하이며, 하나 이상의 문자, 숫자 및 특수문자를 포함해야 합니다."
    )
    private String password;

    private String confirmPassword;

    @Pattern(regexp = "^[가-힣a-zA-Z_]{2,20}$", message = "이름은 특수문자를 제외한 2 ~ 20자이내여야 합니다.")
    private String username;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Pattern(regexp = "^[0-9]{10,11}$", message = "핸드폰 번호는 10~11자리의 숫자만 가능합니다.")
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

