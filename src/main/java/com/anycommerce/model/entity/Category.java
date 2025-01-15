package com.anycommerce.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String categoryCode; // 카테고리 코드

    private int depths; // 현재 카테고리의 깊이 (0 = 최상위, 1 = 하위 등)

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Category parent; // 상위 카테고리

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Category> children = new ArrayList<>(); // 하위 카테고리 목록

    public boolean isTopLevel() {
        return depths == 0;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

}
