package com.anycommerce.model.dto;

import com.anycommerce.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "공통 API 응답 형식")
public class CommonResponse<T> {

    @Schema(description = "에러 코드 (0: 성공, 기타: 실패)", example = "0")
    private int errorCode;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    private String message;

    @Schema(description = "응답 데이터")
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
