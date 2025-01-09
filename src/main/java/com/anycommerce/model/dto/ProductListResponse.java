package com.anycommerce.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductListResponse {

    private String title; // 그룹 제목
    private String subTitle; // 그룹 서브 제목

    private List<ProductResponse> products; // 상품 리스트
    private int totalCount; // 전체 상품 수 (페이징 등을 고려할 때 유용)

    // 추가적으로 페이지 정보가 필요하다면 아래처럼 필드를 추가
    private int currentPage; // 현재 페이지
    private int totalPages;  // 총 페이지 수

}
