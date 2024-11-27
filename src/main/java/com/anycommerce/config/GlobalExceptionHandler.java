package com.anycommerce.config;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex){
        // 404 상태 코드와 예외 메시지 반환
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerGenericException(Exception ex){
        // 500 상태 코드와 기본 메시지를 반환
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("서버 오류 발생.");
    }
}
