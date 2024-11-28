package com.anycommerce.controller;


import com.anycommerce.model.dto.UserAgreementRequestDto;
import com.anycommerce.model.entity.User;
import com.anycommerce.repository.UserRepository;
import com.anycommerce.service.UserAgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/agreement")
public class UserAgreementController {

    private UserAgreementService userAgreementService;
    private UserRepository userRepository;

    /**
     * 필수 약관 동의 여부 확인
     *
     * @param userId 사용자 ID
     * @return 필수 약관 동의 여부
     */
    @GetMapping("/api/check-required")
    public ResponseEntity<Boolean> checkRequiredTermsAgreement(@RequestParam String userId){
        // userId로 User 조회
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));


        boolean hasAgreed = userAgreementService.hasAgreedToAllRequiredTerms(user);

        return ResponseEntity.ok(hasAgreed);
    }


}
