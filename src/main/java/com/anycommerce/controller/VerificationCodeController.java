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
@RequestMapping("/api/checkPhoneNumber")
public class VerificationCodeController {

    private final VerificationCodeService verificationCodeService;

    /**
     * 인증 번호 생성 및 발송 API
     * @param phoneNumber 사용자 전화번호
     * @return 성공 메시지
     */
    @GetMapping("/sendVerificationCode")
    public ResponseEntity<String> sendVerificationNumber(
            @RequestParam String phoneNumber

    ){
        // 인증 번호 생성 (랜덤한 번호값 생성)
        // 전화 번호로 발송 업체 전화번호, 생성한 랜덤 키값 전달
        // 키값은 DB에 저장, phone - key

        verificationCodeService.generateAndSendVerificationCode(phoneNumber);
        return ResponseEntity.ok("인증 번호를 보냈습니다.");

    }


    @GetMapping("/verifyCode")
    public ResponseEntity<String> verifyPhoneNumber(
            @RequestParam String phoneNumber,
            @RequestParam String randomKey
    ){
        boolean isVerifed = verificationCodeService.verifyCode(phoneNumber, randomKey);

        // DB에서 전화번호를 이용해서 randomKey 값을 조회
        // 파라미터의 randomKey와 DB에 저장된 randomKey가 일치하는지 확인
        // 랜덤키값을 만들고 DB에 저장해둔다. 그걸 FE에 반환

        if (isVerifed) {
            return ResponseEntity.ok("인증 되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("잘못된 인증번호입니다.");
        }
    }
}

