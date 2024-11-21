package com.anycommerce.controller;

import com.anycommerce.model.dto.SignUpRequestDto;
import com.anycommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    // 1. HTML Form 요청 처리 (기존 동작 유지)
    @PostMapping("/user")
    public String signup(SignUpRequestDto request) {
        userService.save(request);
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리디렉션
    }

    // 2. JSON 요청 처리 (Postman을 통한 REST API 요청 처리) -> 유저 정보 등록.
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






}

