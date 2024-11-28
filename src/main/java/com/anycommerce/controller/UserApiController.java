package com.anycommerce.controller;

import com.anycommerce.model.dto.SignUpRequestDto;
import com.anycommerce.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    // 1. HTML Form 요청 처리 (기존 View 페이지 이동)
    @PostMapping("/user")
    public String signup(SignUpRequestDto request) {
        userService.registerUser(request);
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리디렉션
    }

    // 2. ID 중복 체크
    @GetMapping("/check-id")
    public ResponseEntity<String> checkUserId(@RequestParam String userId) {
        boolean isDuplicate = userService.checkUserIdDuplicate(userId);
        System.out.println("Checking userId: " + userId + ", isDuplicate: " + isDuplicate);

        if (isDuplicate) {
            return ResponseEntity.badRequest().body("이미 사용중인 아이디입니다.");
        }
        return ResponseEntity.ok().build();
    }

    // 3. 이메일 중복 체크
    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        boolean isDuplicate = userService.checkEmailDuplicate(email);

        if (isDuplicate) {
            return ResponseEntity.badRequest().body("이미 사용중인 이메일입니다.");
        }
        return ResponseEntity.ok().build();
    }


    // *. 최종 등록. -> 최종 엔티티 본으로 수정해야 함.
    @PostMapping("/api/register")
    public ResponseEntity<String> registerUser(@RequestBody SignUpRequestDto request) {
        try {
            userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }


    @Data
    public static class PasswordValidationRequest{
        private String password;
        private String confirmPassword;
    }

}

