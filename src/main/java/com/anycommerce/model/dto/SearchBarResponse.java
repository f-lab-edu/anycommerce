package com.anycommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchBarResponse {
    private String icon;
    private String urlTemplate;
}
