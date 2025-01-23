package com.anycommerce.controller;

import com.anycommerce.model.dto.AddCartItemRequest;
import com.anycommerce.model.dto.CartResponse;
import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public CommonResponse<CartResponse> getCart(@RequestParam Long userId) {
        return CommonResponse.success(cartService.getCart(userId));
    }

    @PostMapping("/add")
    public CommonResponse<CartResponse> addItem(@RequestParam Long userId, @RequestBody AddCartItemRequest request) {
        return CommonResponse.success(cartService.addItem(userId, request));
    }

    @DeleteMapping("/remove")
    public CommonResponse<CartResponse> removeItem(@RequestParam Long userId, @RequestParam Long productId) {
        return CommonResponse.success(cartService.removeItem(userId, productId));
    }

    @PatchMapping("/update")
    public CommonResponse<CartResponse> updateQuantity(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        return CommonResponse.success(cartService.updateQuantity(userId, productId, quantity));
    }
}
