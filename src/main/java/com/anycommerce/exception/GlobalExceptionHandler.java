package com.anycommerce.exception;

import com.anycommerce.model.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

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

        String message = ex.getMessage();

        if(ex.getException() != null){
            ex.printStackTrace();
        }

        if(ex.getConsumer() != null) {
            ex.getConsumer().accept(message);
        } else {
            log.error(message);
        }

        return ResponseEntity
                .status(ex.getErrorCode().getHttpStatus())
                .body(CommonResponse.fromError(ex.getErrorCode(), null));
    }

}
