package com.anycommerce.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddress {
    private String zipcode;
    private String streetAddress;
    private String detailAddress;
}
