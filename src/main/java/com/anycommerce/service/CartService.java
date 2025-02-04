package com.anycommerce.service;

import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.AddCartItemRequest;
import com.anycommerce.model.dto.CartResponse;
import com.anycommerce.model.entity.Cart;
import com.anycommerce.model.entity.CartItem;
import com.anycommerce.model.entity.Product;
import com.anycommerce.model.entity.ProductOption;
import com.anycommerce.repository.CartRepository;
import com.anycommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartResponse getCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return CartResponse.fromEntity(cart);
    }

    public CartResponse addItem(Long userId, AddCartItemRequest request) {
        Cart cart = getOrCreateCart(userId);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND));

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart) // 장바구니 연결
                    .product(product) // 상품 정보 연결
                    .quantity(request.getQuantity()) // 요청된 수량
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()))) // 총 가격 계산
                    .discountPrice(product.getDiscountPrice().multiply(BigDecimal.valueOf(request.getQuantity()))) // 할인 가격 계산
                    .productOptions(mapToProductOptions(request.getOptions())) // 요청에 포함된 옵션
                    .build();
            cart.addItem(newItem);
        }

        cartRepository.save(cart);
        return CartResponse.fromEntity(cart);
    }

    public CartResponse removeItem(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);

        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));

        cartRepository.save(cart);
        return CartResponse.fromEntity(cart);
    }

    public CartResponse updateQuantity(Long userId, Long productId, int quantity) {
        Cart cart = getOrCreateCart(userId);

        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));

        cartRepository.save(cart);
        return CartResponse.fromEntity(cart);
    }

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    return cartRepository.save(newCart);
                });
    }

    private List<ProductOption> mapToProductOptions(List<AddCartItemRequest.ProductOptionRequest> options) {
        if (options == null) {
            return List.of(); // 옵션이 없는 경우 빈 리스트 반환
        }
        return options.stream()
                .map(option -> ProductOption.builder()
                        .optionName(option.getOptionName())
                        .optionDescription(option.getOptionValue()) // optionValue를 optionDescription으로 사용
                        .originalPrice(BigDecimal.ZERO) // 기본값 설정, 필요에 따라 조정 가능
                        .build())
                .toList();
    }
}
