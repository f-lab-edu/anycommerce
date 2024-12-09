package com.anycommerce.model.dto;

import com.anycommerce.exception.ErrorCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse<T> {
    private int errorCode;
    private String message;
    private T content;

    // ErrorCode를 기반으로 CommonResponse 생성
    public static <T> CommonResponse<T> fromError(ErrorCode errorCode, T content) {
        return CommonResponse.<T>builder()
                .errorCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .content(content)
                .build();
    }

    // Success Response
    public static <T> CommonResponse<T> success(T content) {
        return CommonResponse.<T>builder()
                .errorCode(0) // 성공의 경우 0으로 설정
                .message("요청이 성공적으로 처리되었습니다.")
                .content(content)
                .build();
    }
}
