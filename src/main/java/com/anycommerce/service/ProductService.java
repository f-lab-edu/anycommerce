package com.anycommerce.service;

import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.ProductDetailResponse;
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

    /**
     *  상품 상세 조회
     *
     * @param id 상품 ID
     * @return 상품 상세 뷰에 필요한 데이터 리턴
     */
    public ProductDetailResponse getProductDetail(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND));

        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .discountPercentage(product.getDiscountPercentage())
                .mainImageUrl(product.getMainImageUrl())
                .brandName(product.getBrandName())
                .deliveryType(product.getDeliveryType())
                .deliveryInfo(product.getDeliveryInfo())
                .packingType(product.getPackingType())
                .extraInfo(product.getExtraInfo())
                .images(product.getImages())
                .options(product.getOptions().stream()
                        .map(option -> ProductDetailResponse.ProductOptionResponse.builder()
                                .optionSequence(option.getOptionSequence())
                                .productName(option.getOptionName())
                                .originalPrice(option.getOriginalPrice())
                                .currentPrice(option.getCurrentPrice())
                                .build())
                        .toList())
                .build();
    }

}
