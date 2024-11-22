package com.anycommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SmsService {

    private String accessKey;

    private String secretKey;

    private String serviceId;

    private String phone;


}
