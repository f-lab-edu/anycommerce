package com.anycommerce.controller;

import com.anycommerce.model.entity.Terms;
import com.anycommerce.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/terms")
public class TermsController {

    // DB 가져오기
    private final TermsService termsService;

    // 특정 약관 조회 API -> 2개로 나누기 타이틀과 내용이 보이도록
    @GetMapping("/{id}")
    public ResponseEntity<Terms> getTermsById(@PathVariable Long id) {
        return ResponseEntity.ok(termsService.getTermsById(id));
    }



}
