package com.anycommerce.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId; // 사용자 ID (로그인용)

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false, unique = true)
    private String username; // 사용자 이름

    @Column(nullable = false, unique = true)
    private String email; // 이메일 주소

    @Column(nullable = false, unique = true)
    private String phoneNumber; // 전화번호

    // 주소 관련 필드
    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String detailAddress;

}

