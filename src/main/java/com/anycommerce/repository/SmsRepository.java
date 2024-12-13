package com.anycommerce.repository;

import com.anycommerce.model.entity.SmsLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsRepository extends JpaRepository<SmsLog, Long> {
}
