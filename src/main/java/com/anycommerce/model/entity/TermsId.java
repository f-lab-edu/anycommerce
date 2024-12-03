package com.anycommerce.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TermsId implements Serializable {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer version;

}

