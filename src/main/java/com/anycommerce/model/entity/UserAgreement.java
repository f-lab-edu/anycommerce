package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAgreement extends AbstractEntity {

    // 유저 + 약관
    @EmbeddedId
    private UserAgreementId id; // 복합키 사용

    // 동의 여부
    @Column(nullable = false)
    private boolean agreed;
}
