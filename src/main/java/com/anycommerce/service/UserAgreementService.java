package com.anycommerce.service;

import com.anycommerce.model.dto.UserAgreementRequestDto;
import com.anycommerce.model.entity.User;
import com.anycommerce.model.entity.UserAgreement;
import com.anycommerce.repository.UserAgreementRepository;
import com.anycommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class UserAgreementService {

    private final UserAgreementRepository userAgreementRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveUserAgreements(UserAgreementRequestDto requestDto) {
        // 사용자 확인
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 필수 약관 검증
        boolean isRequiredTerm = requestDto.isRequiredTerm1()
                && requestDto.isRequiredTerm2()
                && requestDto.isRequiredTerm3()
                && requestDto.isRequiredTerm4();

        // **필수 약관 검증**
        if (!isRequiredTerm) {
            throw new IllegalArgumentException("모든 필수 약관에 동의해야 회원 가입이 가능합니다.");
        }

        // 선택 약관 (마케팅 약관) 검증
        if (requestDto.isOptionalTerm2()) {
            // 마케팅 약관 동의 시, SMS/Email 동의 여부 검증
            if (requestDto.getSmsConsent() == null && requestDto.getEmailConsent() == null) {
                throw new IllegalArgumentException("마케팅 약관 동의 시 SMS 또는 Email 동의 중 하나를 선택해야 합니다.");
            }
        } else {
            // 마케팅 약관에 동의하지 않은 경우 SMS/Email 동의 값 초기화
            requestDto.setSmsConsent(null);
            requestDto.setEmailConsent(null);
        }

        // UserAgreement 생성 및 저장
        UserAgreement userAgreement = new UserAgreement();
        userAgreement.setUser(user);
        userAgreement.setRequiredTerm(isRequiredTerm); // 필수 약관 동의 여부
        userAgreement.setOptionalTerm1(requestDto.isOptionalTerm1());
        userAgreement.setOptionalTerm2(requestDto.isOptionalTerm2());
        userAgreement.setSmsConsent(requestDto.getSmsConsent());
        userAgreement.setEmailConsent(requestDto.getEmailConsent());
        userAgreement.setAgreedAt(LocalDateTime.now());

        userAgreementRepository.save(userAgreement);
    }
}
