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
    List<Product> findByCategoryId(Long categoryId);

    // 특정 컬렉션의 상품 조회
    @Query("SELECT p FROM Product p JOIN ProductCollectionItem pci ON p.id = pci.product.id WHERE pci.productCollection.id = :collectionId")
    List<Product> findByCollectionId(@Param("collectionId") Long collectionId);
}
