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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/terms")
public class TermsController {

    // DB 가져오기
    private final TermsService termsService;

    // 최신 약관 목록 조회 (title + is_required)
    @GetMapping("/titles")
    public CommonResponse<TermsTitleResponse> getLatestTermsTitles() {
        // TermsTitleResponse를 서비스에서 직접 가져오기
        TermsTitleResponse termsResponse = termsService.getLatestTermsTitles();

        return CommonResponse.<TermsTitleResponse>builder()
                .errorCode(200)
                .message("성공적으로 약관 목록을 가져왔습니다.")
                .content(termsResponse)
                .build();
    }

    // 더보기 특정 약관 내용 조회 (모달 창에서 활용)
    @GetMapping("/{id}")
    public CommonResponse<GetTermResponse> getTermsContentById(@PathVariable TermsId id) {
        Optional<String> contentOptional = termsService.getTermsContentById(id);

        if (contentOptional.isPresent()) {
            // 약관 내용이 있을 경우
            GetTermResponse termResponse = new GetTermResponse(contentOptional.get());
            return CommonResponse.<GetTermResponse>builder()
                    .errorCode(200)
                    .message("Terms content fetched successfully.")
                    .content(termResponse)
                    .build();
        } else {
            // 약관 내용이 없을 경우
            return CommonResponse.<GetTermResponse>builder()
                    .errorCode(404)
                    .message("Terms not found for the given ID.")
                    .content(null)
                    .build();
        }
    }
}
