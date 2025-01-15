package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    private int width;
    private int height;

    private String description;
    private String format;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; // 상품과의 연관 관계

    private boolean isMain;

    public void setProduct(Product product) {
        this.product = product;
        if (product != null && !product.getImages().contains(this)) {
            product.getImages().add(this);
        }
    }
}
