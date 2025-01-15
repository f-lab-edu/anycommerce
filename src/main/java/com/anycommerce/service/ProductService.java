package com.anycommerce.service;

import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.ProductListResponse;
import com.anycommerce.model.dto.ProductResponse;
import com.anycommerce.model.entity.Product;
import com.anycommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 카테고리 기반 상품 조회
    public ProductListResponse getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::fromEntity)
                .toList();

        return ProductListResponse.builder()
                .products(productResponses)
                .build();
    }

    // 특정 컬렉션 상품 조회
    public ProductListResponse getProductsByCollection(Long collectionId) {
        List<Product> products = productRepository.findByCollectionId(collectionId);
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::fromEntity)
                .toList();

        return ProductListResponse.builder()
                .products(productResponses)
                .build();
    }

}
