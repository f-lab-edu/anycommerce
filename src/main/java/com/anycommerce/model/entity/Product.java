package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private BigDecimal discountPrice;

    private int discountPercentage;
    private int comments;
    private int stockQuantity;

    private String mainImageUrl;
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "product_classification",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "classification_id")
    )
    private List<Classification> classifications = new ArrayList<>();

    @Column(unique = true, nullable = false)
    private String productId; // 사용자/외부 시스템용 고유 식별자


    @ManyToOne
    private Category category; // 카테고리 연관 관계

    // 생성 시 ProductID 자동 생성
    @PrePersist
    private void generateProductId() {
        if (productId == null) {
            this.productId = category.getCategoryCode() + "-" + UUID.randomUUID().toString().substring(0, 8);
        }
    }


}
