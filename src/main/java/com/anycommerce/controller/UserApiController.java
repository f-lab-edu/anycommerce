package com.anycommerce.controller;

import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.SignUpRequestDto;
import com.anycommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    // 1. ID 중복 체크
    @GetMapping("/check-id")
    public CommonResponse<Void> checkUserId(@RequestParam String userId) {
        if (userService.checkUserIdDuplicate(userId)) {
            throw new CustomBusinessException(ErrorCode.DUPLICATE_USER_ID);
        }

        return buildSuccessResponse();
    }

    // 2. 이메일 중복 체크
    @GetMapping("/check-email")
    public CommonResponse<Void> checkEmail(@RequestParam String email) {
        if (userService.checkEmailDuplicate(email)) {
            throw new CustomBusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        return buildSuccessResponse();
    }


    @PostMapping("/api/register")
    @ResponseStatus(HttpStatus.CREATED) // 성공 시 201 Created 상태 반환
    public void registerUser(@RequestBody SignUpRequestDto request) {
        userService.registerUser(request);
    }

    // 성공 응답 생성 메서드
    private CommonResponse<Void> buildSuccessResponse() {
        return CommonResponse.<Void>builder()
                .errorCode(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .content(null) // 성공 시에는 content 없음
                .build();
    }

}

