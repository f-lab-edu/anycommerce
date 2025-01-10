package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 상품명
    private BigDecimal price; // 원가
    private BigDecimal discountPrice; // 할인가

    private int discountPercentage; // 할인율
    private int comments; // 리뷰 개수
    private int stockQuantity; // 재고 수량

    private String mainImageUrl; // 대표 이미지 URL
    private String description; // 상품 설명

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>(); // 다중 이미지

    @Column(unique = true, nullable = false)
    private String productCode; // 상품 코드 (e.g., "001001-10001")

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // 카테고리와 연관 관계

    // 연관된 컬렉션 정보
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<ProductCollection> productCollections = new ArrayList<>();


    @PostPersist
    private void generateProductCode() {
        if (productCode == null) {
            this.productCode = category.getCategoryCode() + "-" + id; // 카테고리 코드 + ID로 고유 코드 생성
        }
    }

}
