package com.anycommerce.controller;

import com.anycommerce.model.dto.SignUpRequestDto;
import com.anycommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(SignUpRequestDto request){
        userService.save(request);
        return "redirect:/login";
    }

}
