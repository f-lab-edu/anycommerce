package com.anycommerce.repository;

import com.anycommerce.model.entity.Image;
import com.anycommerce.model.entity.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByType(ImageType type);
    Optional<Image> findById(Long id);
    List<Image> findByProductId(Long productId);
}
