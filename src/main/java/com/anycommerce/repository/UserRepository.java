package com.anycommerce.repository;

import com.anycommerce.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

// 이메일로 사용자를 식별하기 위해 Repo로 구성.
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // 사용자 ID (userId)로 조회
    Optional<User> findByUserId(String userId);

    // ID 중복 체크
    boolean existsByUserId(String userId);

    // Email 중복 체크
    boolean existsByEmail(String email);

    // 전화번호 중복 체크
    boolean existsByPhoneNumber(String phoneNumber);


}
