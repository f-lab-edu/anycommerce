package com.anycommerce.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private int discountPercentage;
    private int comments;

    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "product_classification",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "classification_id")
    )
    private List<Classification> classifications = new ArrayList<>();

}
