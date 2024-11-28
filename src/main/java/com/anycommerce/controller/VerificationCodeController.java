package com.anycommerce.controller;

import com.anycommerce.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/verification")
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;

    /**
     * 인증 번호 생성 및 발송 API
     * @param phoneNumber 사용자 전화번호
     * @return 성공 메시지
     */
    @GetMapping("/send")
    public ResponseEntity<String> sendVerificationNumber(
            @RequestParam String phoneNumber

    ){

        verificationCodeService.generateAndSendCode(phoneNumber);
        return ResponseEntity.ok("인증 번호를 보냈습니다.");

    }


    @GetMapping("/verify")
    public ResponseEntity<String> verifyCode(
            @RequestParam String phoneNumber,
            @RequestParam String randomKey
    ){
        verificationCodeService.verifyCode(phoneNumber, randomKey);
        return ResponseEntity.ok("인증 성공");



    }
}

