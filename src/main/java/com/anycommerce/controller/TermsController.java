package com.anycommerce.controller;

import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.GetTermResponse;
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
    public CommonResponse<List<TermsTitleResponse>> getLatestTermsTitles() {
        List<TermsTitleResponse> termsList = termsService.getLatestTermsTitles();

        return CommonResponse.<List<TermsTitleResponse>>builder()
                .code(200)
                .message("성공적으로 약관 목록을 가져왔습니다.")
                .payload(termsList)
                .build();
    }

    // 더보기 특정 약관 내용 조회 (모달 창에서 활용)
    @GetMapping("/{id}")
    public CommonResponse<GetTermResponse> getTermsContentById(@PathVariable TermsId id) {
        String content = termsService.getTermsContentById(id);
        GetTermResponse termResponse = new GetTermResponse(content);

        return CommonResponse.<GetTermResponse>builder()
                .code(200)
                .message("성공적으로 약관 내용을 가져왔습니다.")
                .payload(termResponse)
                .build();
    }

}
