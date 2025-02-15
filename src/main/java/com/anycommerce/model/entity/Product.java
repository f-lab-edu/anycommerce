package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product extends AbstractEntity {

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

    private String brandName; // 브랜드명
    private String deliveryType; // 배송 타입 (e.g., "샛별배송")
    private String deliveryInfo; // 배송 정보
    private String packingType; // 포장 타입
    private String extraInfo; // 추가 정보

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>(); // 다중 이미지

    @Column(unique = true, nullable = false)
    private String productCode; // 상품 코드 (e.g., "001001-10001")

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // 카테고리와 연관 관계


    @PostPersist
    private void generateProductCode() {
        if (productCode == null) {
            this.productCode = category.getCategoryCode() + "-" + id; // 카테고리 코드 + ID로 고유 코드 생성
        }
    }

    public void addImage(Image image) {
        this.images.add(image);
        image.setProduct(this);
    }

    public void removeImage(Image image) {
        this.images.remove(image);
        image.setProduct(null);
    }

    @ElementCollection
    @CollectionTable(name = "product_options", joinColumns = @JoinColumn(name = "product_id"))
    private List<ProductOption> options = new ArrayList<>(); // 옵션 리스트

}
