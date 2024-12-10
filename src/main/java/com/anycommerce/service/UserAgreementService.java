package com.anycommerce.service;

import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.entity.*;
import com.anycommerce.repository.TermsRepository;
import com.anycommerce.repository.UserAgreementRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class UserAgreementService {

    private final UserAgreementRepository userAgreementRepository;
    private final TermsRepository termsRepository;


    /**
     * 필수 약관 동의 여부 확인
     *
     * @param user 사용자
     * @return 모든 필수 약관에 동의했는지 여부
     */
    @Transactional(readOnly = true)
    public boolean hasAgreedToAllRequiredTerms(User user) {
        List<Terms> requiredTerms = termsRepository.findAllByIsRequired(true);
        List<UserAgreement> userAgreements = userAgreementRepository.findAllByIdUserAndAgreed(user, true);

        return requiredTerms.stream().allMatch(term -> isUserAgreedToTerm(userAgreements, term));
    }

    private boolean isUserAgreedToTerm(List<UserAgreement> userAgreements, Terms term) {
        return userAgreements.stream().anyMatch(agreement ->
                Objects.equals(agreement.getId().getTerms().getId().getTitle(), term.getId().getTitle()) &&
                        Objects.equals(agreement.getId().getTerms().getId().getVersion(), term.getId().getVersion()));
    }

    /**
     * 약관 동의 저장
     *
     * @param user  사용자
     * @param termsId 약관 ID
     * @param agreed 동의 여부
     */
    @Transactional
    public void saveUserAgreement(User user, TermsId termsId, boolean agreed) {

        Terms terms = termsRepository.findById(termsId)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND_TERMS));

        UserAgreement userAgreement = userAgreementRepository.findByIdUserAndIdTerms(user, terms)
                .orElseGet(() -> createNewUserAgreement(user, terms));

        userAgreement.setAgreed(agreed);
        userAgreementRepository.save(userAgreement);
    }

    private UserAgreement createNewUserAgreement(User user, Terms terms) {
        UserAgreementId userAgreementId = new UserAgreementId(user, terms);
        return new UserAgreement(userAgreementId, false);
    }


}
