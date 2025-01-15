package com.anycommerce.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductCollectionItemId implements Serializable {
    private Long collectionId;
    private Long productId;
}
