package com.anycommerce.model.dto;

import lombok.Data;

@Data
public class GetTermResponse {
    private String term;

    public GetTermResponse(String term) {
        this.term = term;
    }
}
