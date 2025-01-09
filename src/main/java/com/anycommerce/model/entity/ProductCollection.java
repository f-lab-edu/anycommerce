package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 컬렉션 이름 (예: "new-year", "ranking")
    private String title; // 컬렉션 제목
    private String subTitle; // 컬렉션 서브 제목
    private boolean isDynamic; // 동적 생성 여부 (예: 매출 랭킹, 최근 추가된 상품 등)

    @OneToMany(mappedBy = "productCollection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCollectionItem> items = new ArrayList<>();
}
