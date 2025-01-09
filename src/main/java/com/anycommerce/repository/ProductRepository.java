package com.anycommerce.repository;

import com.anycommerce.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 특정 카테고리의 상품 조회
    @Query("SELECT p FROM Product p WHERE p.subCategory.id = :subCategoryId")
    List<Product> findBySubCategory(@Param("subCategoryId") Long subCategoryId);

    // 특정 컬렉션의 상품 조회
    @Query("SELECT p FROM Product p JOIN p.productCollections pc WHERE pc.id = :collectionId")
    List<Product> findByCollectionId(@Param("collectionId") Long collectionId);
}
