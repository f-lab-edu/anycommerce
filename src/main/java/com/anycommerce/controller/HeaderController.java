package com.anycommerce.controller;

import com.anycommerce.model.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/header")
@RequiredArgsConstructor
public class HeaderController {

    // 작업 안 끝남
    @GetMapping
    public CommonResponse<HeaderResponse> getHeaderInfo(){
        HeaderResponse response = HeaderResponse.builder()
                .logo(new LogoResponse("/images/logo.jpg", "/main"))
                .searchBar(new SearchBarResponse("/images/search", "/serach?sword={query}"))
                .shortcuts(List.of(
                        new ShortcutResponse("/images/address-icon.jpg", "/address/shipping-address/list"),
                        new ShortcutResponse("/images/favorites-icon.jpg", "/mypage/pick/list"),
                        new ShortcutResponse("/images/cart-icon.jpg", "/cart")
                ))
                .build();

        return CommonResponse.success(response);
    }

}
