package com.anycommerce.controller;

import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.GetTermResponse;
import com.anycommerce.model.dto.TermsTitleResponse;
import com.anycommerce.model.entity.TermsId;
import com.anycommerce.service.TermsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/terms")
public class TermsController {

    // DB 가져오기
    private final TermsService termsService;

    // 최신 약관 목록 조회 (title + is_required)
    @Operation(summary = "최신 약관 제목 및 필수 여부 조회", description = "최신 약관의 제목과 필수 여부를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(schema = @Schema(implementation = TermsTitleResponse.class)))
    @GetMapping("/titles")
    public CommonResponse<TermsTitleResponse> getLatestTermsTitles() {
        // TermsTitleResponse를 서비스에서 직접 가져오기
        TermsTitleResponse termsResponse = termsService.getLatestTermsTitles();

        return buildSuccessResponse(termsResponse);
    }

    // 더보기 특정 약관 내용 조회 (모달 창에서 활용)
    @Operation(summary = "약관 내용 조회", description = "특정 약관 ID를 기반으로 약관의 내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "약관 내용 조회 성공", content = @Content(schema = @Schema(implementation = GetTermResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "404", description = "요청한 약관이 존재하지 않습니다", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/{id}")
    public CommonResponse<GetTermResponse> getTermsContentById(@PathVariable TermsId id) {

        String content = termsService.getTermsContentById(id);
        GetTermResponse termResponse = new GetTermResponse(content);

        return buildSuccessResponse(termResponse);
    }

    // (성공) 응답 빌더
    private <T> CommonResponse<T> buildSuccessResponse(T content) {
        return CommonResponse.<T>builder()
                .errorCode(ErrorCode.SUCCESS.getCode())
                .message(null)
                .content(content)
                .build();
    }


}
