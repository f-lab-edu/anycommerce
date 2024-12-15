package com.anycommerce.exception;


import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Getter
public class CustomBusinessException extends RuntimeException{

    private final ErrorCode errorCode;
    private final Exception exception;
    private final Consumer<String> consumer;
    private final Map<String, Object> parameter;

    public CustomBusinessException(ErrorCode errorCode, Consumer<String> consumer, Map<String, Object> parameter){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.exception = null;
        this.consumer = consumer;
        this.parameter = parameter;
    }

    public CustomBusinessException(ErrorCode errorCode, Exception exception, Consumer<String> consumer , Map<String, Object> parameter){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.exception = exception;
        this.consumer = consumer;
        this.parameter = parameter;
    }

    public CustomBusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.exception = null;
        this.consumer = null;
        this.parameter = new HashMap<>();
    }

}
