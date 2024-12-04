package com.anycommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TermsTitleResponse {

    private String title;
    private boolean isRequired;

}
