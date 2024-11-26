package com.anycommerce.model.dto;

import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class SignUpRequestDto {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Size(min = 5, max = 20, message = "아이디는 5자 이상, 20자 이하여야 합니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상, 20자 이하여야 합니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,20}$",
            message = "비밀번호는 하나 이상의 문자, 숫자, 특수문자를 포함해야 합니다."
    )
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(min = 2, max = 20, message = "이름은 2자 이상, 20자 이하여야 합니다.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    @Pattern(
            regexp = "^[0-9]{10,11}$",
            message = "전화번호는 10자리 또는 11자리의 숫자만 가능합니다."
    )
    private String phoneNumber;

    @NotBlank(message = "우편번호는 필수 입력 값입니다.")
    private String zipcode;

    @NotBlank(message = "도로명 주소는 필수 입력 값입니다.")
    private String streetAddress;

    @NotBlank(message = "상세 주소는 필수 입력 값입니다.")
    private String detailAddress;

    // 약관 동의 상태 (약관 ID -> 동의 여부)
    private Map<Long, Boolean> agreement;

}

