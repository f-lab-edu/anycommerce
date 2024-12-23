package com.anycommerce.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
@Schema(description = "회원가입 요청 DTO") // DTO 설명 추가
public class SignUpRequestDto {

    @NotBlank
    @Size(min = 5, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    @Schema(description = "사용자 ID", example = "user123")
    private String userId;

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,20}$")
    @Schema(description = "비밀번호 (영문, 숫자, 특수문자 포함) 최소 8자 ~ 최대 20자", example = "Password@123")
    private String password;

    @NotBlank
    @Size(min = 2, max = 20)
    @Schema(description = "사용자 이름", example = "John Doe")
    private String username;

    @NotBlank
    @Email
    @Schema(description = "사용자 이메일", example = "example@example.com")
    private String email;

    @NotBlank
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNumber;

    @NotBlank
    @Pattern(regexp = "^[0-9]{5}$")
    @Schema(description = "우편번호", example = "12345")
    private String zipcode;

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]*$")
    @Schema(description = "도로명 주소", example = "서울시 강남구 테헤란로 123")
    private String streetAddress;

    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣\\s]*$")
    @Schema(description = "상세 주소", example = "101호")
    private String detailAddress;

    @Schema(description = "약관 동의 여부 (약관 ID와 동의 여부)", example = "{1: true, 2: false, 3: true, 4: true}")
    private Map<Long, Boolean> agreement;

}

