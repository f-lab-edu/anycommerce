package com.anycommerce.exception;

import com.anycommerce.model.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.fromError(ErrorCode.INTERNAL_SERVER_ERROR, null));
    }

    // 디버그용
    @ExceptionHandler(CustomBusinessException.class)
    public ResponseEntity<CommonResponse<?>> handleCustomBusinessException(CustomBusinessException ex) {

        // 에러 메시지 포맷팅
        String formattedMessage = String.format(
                "Error occurred at [%s.%s:%d] - %s",
                ex.getStackTrace()[0].getClassName(),  // 클래스 이름
                ex.getStackTrace()[0].getMethodName(), // 메서드 이름
                ex.getStackTrace()[0].getLineNumber(), // 라인 번호
                ex.getMessage()                        // 에러 메시지
        );

        // 로그 출력
        if (ex.getException() != null) {
            log.error(formattedMessage, ex.getException()); // 상세 에러 로그
        } else {
            log.error(formattedMessage); // 기본 에러 로그
        }

        // Consumer 로 메시지 전달
        if (ex.getConsumer() != null) {
            ex.getConsumer().accept(formattedMessage);
        }

        // Response 반환
        return ResponseEntity
                .status(ex.getErrorCode().getHttpStatus())
                .body(CommonResponse.fromError(ex.getErrorCode(), null));
    }

}
