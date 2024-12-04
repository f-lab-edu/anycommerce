package com.anycommerce.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponse<T> {
    private int code;
    private String message;
    private T payload;

    public CommonResponse(int code, String message, T payload) {
        this.code = code;
        this.message = message;
        this.payload = payload;
    }


    public CommonResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResponse() {
    }
}
