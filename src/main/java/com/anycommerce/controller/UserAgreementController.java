package com.anycommerce.controller;


import com.anycommerce.model.dto.UserAgreementRequestDto;
import com.anycommerce.service.UserAgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/agreement")
public class UserAgreementController {

    private UserAgreementService userAgreementService;

    @PostMapping
    public ResponseEntity<String> saveUserAgreements(@RequestBody UserAgreementRequestDto requestDto){
        // 약관 동의 성공적으로 저장
        try {
            userAgreementService.saveUserAgreements(requestDto);
            return ResponseEntity.ok("약관 동의가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            // 약관 동의 저장 중 오류 발생 Exception.
            return ResponseEntity.badRequest().body("약관 동의 저장 중 오류 발생 : " + e.getMessage());
        }
    }

}
