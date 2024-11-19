package com.anycommerce.controller;

import com.anycommerce.model.dto.SignUpRequestDto;
import com.anycommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// 각각 API 만들고 정리할 예정

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    // 1. HTML Form 요청 처리 (기존 동작 유지)
//    @PostMapping("/user")
//    public String signup(SignUpRequestDto request) {
//        userService.save(request);
//        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리디렉션
//    }

    // 2. JSON 요청 처리 (Postman을 통한 REST API 요청 처리) -> 사실상 이게 유저 정보 등록.
    @PostMapping("/api/register")
    public ResponseEntity<String> registerUser(@RequestBody SignUpRequestDto request) {
        try {
            userService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    /*   Controller 만들어보기  */


    @GetMapping("/send-verification-number")
    public void sendVerificationNumber(
            @RequestParam String phoneNumber
    ){
        // 인증 번호 생성 (랜덤한 번호값 생성)
        // 전화 번호로 발송 업체 전화번호, 생성한 랜덤 키값 전달
        // 키값은 DB에 저장, phone - key
    }


    @GetMapping("/verify-code")
    public numResponse verifyPhoneNumber(
            String phoneNumber,
            String randomKey
    ){
        // DB에서 전화번호를 이용해서 randomKey 값을 조회
        // 파라미터의 randomKey와 DB에 저장된 randomKey가 일치하는지 확인
        // 랜덤키값을 만들고 DB에 저장해둔다. 그걸 FE에 반환

        return new numResponse();
    }



    // 회원 가입 버튼 누르기 -> 이미 위에 있음
//    @GetMapping("/api/signup")
//    public void userSubmit(){
//
//    }




}

class numResponse{
    private String randomValue;
}
