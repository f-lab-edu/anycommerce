package com.anycommerce.controller;

import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.ProductResponse;
import com.anycommerce.model.entity.Product;
import com.anycommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 특정 상품 조회 API
     *
     * @param id 상품 ID
     * @return 상품 정보
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "메인 화면 상품 표기 정보 조회",
            description = "상품 ID를 기반으로 메인화면에 표기할 상품 정보들을 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "상품 조회 성공",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 오류",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))
            )
    })
    public CommonResponse<ProductResponse> getProductOnMain(@PathVariable Long id) {
        Product product = productService.getProductById(id);

        // 댓글 수 제한 로직
        int comments = product.getComments();
        if (comments > 10000) {
            comments = 9999;
        } else if (comments > 1000) {
            comments = 999; // 1000 이상이면 999+로 표시
        }

        ProductResponse response = ProductResponse.fromEntity(product);
        response.setComments(comments); // 제한된 댓글 수로 설정

        return CommonResponse.success(response);
    }

}
