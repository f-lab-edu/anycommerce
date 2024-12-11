package com.anycommerce.repository;

import com.anycommerce.model.entity.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
}
