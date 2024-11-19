package com.anycommerce.controller;

import com.anycommerce.model.entity.Terms;
import com.anycommerce.repository.TermsRepository;
import com.anycommerce.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/terms")
public class TermsController {

    // DB 가져오기
    private final TermsService termsService;

    // 모든 약관 조회 API
    @GetMapping
    public ResponseEntity<List<Terms>> getTerms(){
        return ResponseEntity.ok(termsService.getAllTerms());
    }

    // 특정 약관 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<Terms> getTermsById(@PathVariable Long id) {
        return ResponseEntity.ok(termsService.getTermsById(id));
    }



}
