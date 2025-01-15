package com.anycommerce.service;


import com.anycommerce.model.dto.BannerResponse;
import com.anycommerce.model.dto.CommonResponse;
import com.anycommerce.model.dto.SignUpRequestDto;
import com.anycommerce.model.entity.*;
import com.anycommerce.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;

public class UserRegistrationTest extends IntegrationTestBase{

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TermsRepository termsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @MockBean
    private SmsService smsService;

    @BeforeEach
    void setUp() {
        // 1. 약관 데이터 삽입
        Terms terms1 = new Terms();
        terms1.setId(new TermsId("Privacy Policy", 1));
        terms1.setContent("This is the privacy policy.");
        terms1.setRequired(true);
        terms1.setActive(true);

        Terms terms2 = new Terms();
        terms2.setId(new TermsId("Terms of Service", 1));
        terms2.setContent("These are the terms of service.");
        terms2.setRequired(true);
        terms2.setActive(true);

        termsRepository.save(terms1);
        termsRepository.save(terms2);

        // 2. 카테고리 데이터 삽입
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setCategoryCode("001");
        electronics.setDepths(0);

        categoryRepository.save(electronics);

        // 3. 상품 데이터 삽입
        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(BigDecimal.valueOf(1000));
        product.setDiscountPrice(BigDecimal.valueOf(900));
        product.setDiscountPercentage(10);
        product.setStockQuantity(50);
        product.setMainImageUrl("http://example.com/laptop.png");
        product.setDescription("High-performance laptop");
        product.setCategory(electronics);

        productRepository.save(product);

        // 4. 배너 데이터 삽입
        Banner banner = new Banner();
        banner.setImageUrl("http://example.com/banner.png");
        banner.setLinkUrl("http://example.com/promotion");
        banner.setDescription("Promotional Banner");
        banner.setActive(true);
        banner.setOrderIndex(1);
        banner.setDisplayDuration(10);

        bannerRepository.save(banner);
    }


    @Test
    void testUserRegistration(){
        // given : 회원 가입 요청 데이터
        String phoneNumber = "010-1234-5678";
        String verificationCode = "123456";

        // Mock: SMS 발송 로직 우회
        doNothing().when(smsService).sendSms(anyString(), eq(phoneNumber), eq(verificationCode));

        // Step 1: 인증번호 생성 및 발송
        ResponseEntity<CommonResponse<Void>> generateResponse = restTemplate.exchange(
                "/api/verification/generate",
                HttpMethod.POST,
                new HttpEntity<>(phoneNumber),
                new ParameterizedTypeReference<>() {});
        assertThat(generateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(generateResponse.getBody()).isNotNull();
        assertThat(generateResponse.getBody().getErrorCode()).isEqualTo(0);

        // Step 2: 인증번호 검증
        Map<String, String> verifyRequest = Map.of("phoneNumber", phoneNumber, "randomKey", verificationCode);
        ResponseEntity<CommonResponse<Void>> verifyResponse = restTemplate.exchange(
                "/api/verification/verify",
                HttpMethod.POST,
                new HttpEntity<>(verifyRequest),
                new ParameterizedTypeReference<>() {});
        assertThat(verifyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(verifyResponse.getBody()).isNotNull();
        assertThat(verifyResponse.getBody().getErrorCode()).isEqualTo(0);

        // Step 3: 회원가입 요청 데이터
        SignUpRequestDto requestDto = new SignUpRequestDto();
        requestDto.setUserId("testuser");
        requestDto.setPassword("Password@123");
        requestDto.setUsername("Test User");
        requestDto.setEmail("test@example.com");
        requestDto.setPhoneNumber(phoneNumber);
        requestDto.setZipcode("12345");
        requestDto.setStreetAddress("서울시 강남구 테헤란로 123");
        requestDto.setDetailAddress("101호");
        requestDto.setAgreement(Map.of(1L, true, 2L, true));

        // Step 4: 회원가입
        ResponseEntity<Void> registerResponse = restTemplate.postForEntity(
                "/api/users/api/register", requestDto, Void.class);
        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Step 5: ID와 이메일 중복 체크
        ResponseEntity<CommonResponse<Void>> idCheckResponse = restTemplate.exchange(
                "/api/users/check-id?userId=testuser",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        assertThat(idCheckResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT); // 중복되므로 예외 발생

        ResponseEntity<CommonResponse<Void>> emailCheckResponse = restTemplate.exchange(
                "/api/users/check-email?email=test@example.com",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        assertThat(emailCheckResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT); // 중복되므로 예외 발생

        // Step 6: 최종 데이터 검증
        User savedUser = userRepository.findByUserId("testuser").orElseThrow();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(savedUser.getUsername()).isEqualTo("Test User");

        boolean verificationExists = verificationCodeRepository.findByPhoneNumber(phoneNumber).isPresent();
        assertThat(verificationExists).isFalse(); // 인증 데이터 삭제 확인
    }

}
