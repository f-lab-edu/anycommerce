package com.anycommerce.controller;

import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.GetTermResponse;
import com.anycommerce.model.dto.TermsTitleResponse;
import com.anycommerce.model.entity.TermsId;
import com.anycommerce.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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

        return buildSuccessResponse(termsResponse, "약관 목록을 성공적으로 가져왔습니다.");
    }

    // 더보기 특정 약관 내용 조회 (모달 창에서 활용)
    @GetMapping("/{id}")
    public CommonResponse<GetTermResponse> getTermsContentById(@PathVariable TermsId id) {
        Optional<String> contentOptional = termsService.getTermsContentById(id);

        // Optional 처리 대신 예외 처리로 변경
        String content = termsService.getTermsContentById(id)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND));

        GetTermResponse termResponse = new GetTermResponse(content);
        return buildSuccessResponse(termResponse, "약관 내용을 성공적으로 가져왔습니다.");
    }

    // (성공) 응답 빌더
    private <T> CommonResponse<T> buildSuccessResponse(T content, String message) {
        return CommonResponse.<T>builder()
                .errorCode(ErrorCode.SUCCESS.getCode())
                .message(message)
                .content(content)
                .build();
    }


}
