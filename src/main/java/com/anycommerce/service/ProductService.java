package com.anycommerce.service;

import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.entity.Product;
import com.anycommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product getProductById(Long id) {
        // 데이터베이스에서 상품 조회
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND));
    }

}
