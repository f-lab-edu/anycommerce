package com.anycommerce.model.entity;


import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserAgreementId implements Serializable {

    // User 객체를 참조
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // Terms 객체를 참조
    @ManyToOne(fetch = FetchType.LAZY)
    private Terms terms;

}