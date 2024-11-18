package com.anycommerce.controller;

import com.anycommerce.model.entity.Terms;
import com.anycommerce.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/terms")
public class TermsController {

    // DB 가져오기
    private final TermsRepository termsRepository;

    // 약관의 id에 따라서 약관 정보 보여주기
    @GetMapping("/{id}")
    public ResponseEntity<String> getTermsById(@PathVariable Long id) {
        return termsRepository.findById(id)
                .map(terms -> ResponseEntity.ok(terms.getContent()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 약관이 없습니다."));
    }



}
