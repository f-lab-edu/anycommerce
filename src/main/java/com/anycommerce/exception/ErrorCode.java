package com.anycommerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 성공 응답
    SUCCESS(HttpStatus.OK, 0, "요청이 성공적으로 처리되었습니다."),

    // 공통 에러
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, 4000, "요청이 유효하지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 4001, "인증이 필요합니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, 4002, "입력값 검증 오류가 발생했습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, 4003, "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, 4004, "요청한 자원을 찾을 수 없습니다."),

    // 토큰 관련 에러 (사용자에게는 하나의 메시지로 노출)
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, 4101, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 4102, "유효하지 않은 토큰입니다."),
    MALFORMED_TOKEN(HttpStatus.BAD_REQUEST, 4103, "유효하지 않은 토큰입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 4104, "유효하지 않은 토큰입니다."),
    INVALID_USER(HttpStatus.UNAUTHORIZED, 4105, "유효하지 않은 토큰입니다."),
    UNEXPECTED_USER(HttpStatus.UNAUTHORIZED, 4106, "유효하지 않은 토큰입니다."),
    UNEXPECTED_TOKEN(HttpStatus.UNAUTHORIZED, 4107, "유효하지 않은 토큰입니다."),


    // SMS 관련 에러
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, 4290, "요청이 너무 많습니다. 잠시 후 다시 시도해주세요."),
    TOO_MANY_SMS_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, 4291, "SMS 요청이 너무 많습니다. 잠시 후 다시 시도해주세요."),

    // 사용자 관련 에러
    DUPLICATE_USER_ID(HttpStatus.BAD_REQUEST, 4010, "이미 사용 중인 아이디입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, 4011, "이미 사용 중인 이메일입니다."),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, 4012, "아이디는 5자 이상, 20자 이하여야 하며, 영문, 숫자, 밑줄(_)만 사용할 수 있습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, 4013, "비밀번호는 8자 이상, 20자 이하여야 하며, 하나 이상의 문자, 숫자, 특수문자를 포함해야 합니다."),
    INVALID_USERNAME(HttpStatus.BAD_REQUEST, 4014, "이름은 2자 이상, 20자 이하여야 합니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, 4015, "유효하지 않은 이메일 형식입니다."),
    INVALID_PHONE_NUMBER(HttpStatus.BAD_REQUEST, 4016, "전화번호는 '010-XXXX-XXXX' 형식이어야 합니다."),
    INVALID_ZIPCODE(HttpStatus.BAD_REQUEST, 4017, "우편번호는 5자리 숫자여야 합니다."),
    INVALID_STREET_ADDRESS(HttpStatus.BAD_REQUEST, 4018, "도로명 주소는 특수문자를 포함할 수 없으며, 최대 100자까지 가능합니다."),
    INVALID_DETAIL_ADDRESS(HttpStatus.BAD_REQUEST, 4019, "상세 주소는 특수문자를 포함할 수 없으며, 최대 50자까지 가능합니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, 4020, "인증번호가 일치하지 않습니다."),
    EXPIRED_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, 4021, "인증번호가 만료되었습니다."),
    TOO_MANY_VERIFICATION_ATTEMPTS(HttpStatus.TOO_MANY_REQUESTS, 4022, "인증번호 입력 시도가 너무 많습니다."),
    VERIFICATION_NOT_REQUESTED(HttpStatus.BAD_REQUEST, 4023, "인증 요청이 존재하지 않습니다."),
    BLACKLISTED_PHONE_NUMBER(HttpStatus.FORBIDDEN, 4024, "해당 전화번호는 더 이상 인증할 수 없습니다."),


    // 약관 관련 에러
    NOT_FOUND_TERMS(HttpStatus.NOT_FOUND, 4030, "존재하지 않는 약관입니다."),
    NOT_AGREED_REQUIRED_TERMS(HttpStatus.BAD_REQUEST, 4031, "필수 약관에 동의하지 않았습니다."),

    // 기타 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "서버 내부 오류가 발생했습니다."),
    SMS_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "SMS 발송에 실패했습니다."),

    // 이미지 에러
    MAIN_IMAGE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, 4050, "대표 이미지를 불러오는 데 실패했습니다."),
    IMAGE_LOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 5002, "이미지를 불러오는 데 실패했습니다."),

    // 주문 에러
    ORDER_CANNOT_BE_CANCELLED(HttpStatus.BAD_REQUEST, 4100, "해당 주문을 취소할 수 없습니다.");

    private final HttpStatus httpStatus; // Spring의 HttpStatus
    private final int code; // 프로젝트에서 정의한 에러 코드
    private final String message; // 에러 메시지

    ErrorCode(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

}
