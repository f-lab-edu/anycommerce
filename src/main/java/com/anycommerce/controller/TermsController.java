package com.anycommerce.controller;

import com.anycommerce.model.dto.TermsTitleResponse;
import com.anycommerce.model.entity.TermsId;
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

    // 최신 약관 목록 조회 (title + is_required)
    @GetMapping("/titles")
    public ResponseEntity<List<TermsTitleResponse>> getLatestTermsTitles() {
        return ResponseEntity.ok(termsService.getLatestTermsTitles());
    }

    // 더보기 특정 약관 내용 조회 (모달 창에서 활용)
    @GetMapping("/{id}")
    public ResponseEntity<String> getTermsContentById(@PathVariable TermsId id) {
        String content = termsService.getTermsContentById(id);
        return ResponseEntity.ok(content);
    }



}
