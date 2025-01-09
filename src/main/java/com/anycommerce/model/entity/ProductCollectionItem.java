package com.anycommerce.model.entity;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCollectionItem {

    @EmbeddedId
    private ProductCollectionItemId id;

    @ManyToOne
    @MapsId("collectionId")
    private ProductCollection productCollection;

    @ManyToOne
    @MapsId("productId")
    private Product product;

    private int displayOrder; // 컬렉션 내 상품 정렬 우선순위
}
