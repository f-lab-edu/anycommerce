package com.anycommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HeaderResponse {
    private LogoResponse logo;
    private SearchBarResponse searchBar;
    private List<ShortcutResponse> shortcuts;
}

