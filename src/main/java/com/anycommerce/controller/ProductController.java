package com.anycommerce.controller;

import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.ProductListResponse;
import com.anycommerce.model.dto.ProductResponse;
import com.anycommerce.model.entity.Product;
import com.anycommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 카테고리 기반 상품 조회
     *
     * @param subCategoryId 하위 카테고리 ID
     * @return 해당 카테고리의 상품 리스트
     */
    @GetMapping("/category")
    public CommonResponse<ProductListResponse> getProductsByCategory(@RequestParam Long subCategoryId) {
        ProductListResponse response = productService.getProductsByCategory(subCategoryId);
        return CommonResponse.success(response);
    }

    /**
     * 특정 컬렉션 상품 조회
     *
     * @param collectionId 컬렉션 ID
     * @return 해당 컬렉션의 상품 리스트
     */
    @GetMapping("/collection")
    public CommonResponse<ProductListResponse> getProductsByCollection(@RequestParam Long collectionId) {
        ProductListResponse response = productService.getProductsByCollection(collectionId);
        return CommonResponse.success(response);
    }

}
