package com.anycommerce.service;

import com.anycommerce.model.dto.UserAgreementRequestDto;
import com.anycommerce.model.entity.Terms;
import com.anycommerce.model.entity.User;
import com.anycommerce.model.entity.UserAgreement;
import com.anycommerce.repository.TermsRepository;
import com.anycommerce.repository.UserAgreementRepository;
import com.anycommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserAgreementService {

    private final UserAgreementRepository userAgreementRepository;
    private final TermsRepository termsRepository;
    private final UserRepository userRepository;


    /**
     * 필수 약관 동의 여부 확인
     *
     * @param user 사용자
     * @return 모든 필수 약관에 동의했는지 여부
     */
    public boolean hasAgreedToAllRequiredTerms(User user) {

        List<Terms> requiredTerms = termsRepository.findAllByIsRequired(true);
        List<UserAgreement> userAgreements = userAgreementRepository.findAllByUserAndAgreed(user, true);

        // 필수 약관의 ID가 사용자가 동의한 약관 목록에 모두 포함되어있는지 확인
        return requiredTerms.stream()
                .allMatch(term -> userAgreements.stream()
                        .anyMatch(agreement -> agreement.getTerms().getId().equals(term.getId())));

    }

    /**
     * 약관 동의 저장
     *
     * @param user  사용자
     * @param termId 약관 ID
     * @param agreed 동의 여부
     */
    @Transactional
    public void saveUserAgreement(User user, Long termId, boolean agreed) {

        Terms terms = termsRepository.findById(termId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 약관입니다."));

        // 약관 동의가 이미 존재 하면 업데이트, 아니면 새로 생성
        UserAgreement userAgreement = userAgreementRepository.findByUserAndTerms(user, terms)
                .orElse(new UserAgreement());

        userAgreement.setUser(user);
        userAgreement.setTerms(terms);
        userAgreement.setAgreed(agreed);
        userAgreementRepository.save(userAgreement);
    }


}
