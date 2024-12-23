package com.anycommerce.controller;

import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.CreateAccessTokenRequest;
import com.anycommerce.model.dto.CreateAccessTokenResponse;
import com.anycommerce.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @Operation(
            summary = "새로운 Access Token 생성",
            description = "Refresh Token을 기반으로 새로운 Access Token을 생성합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "새로운 Access Token 생성 성공",
                    content = @Content(schema = @Schema(implementation = CreateAccessTokenResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 Refresh Token",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패 (Refresh Token 만료 또는 유효하지 않음)",
                    content = @Content(schema = @Schema(implementation = CommonResponse.class))
            )
    })
    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createAccessToken
            (@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
