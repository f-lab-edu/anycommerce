package com.anycommerce.service;

import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.TermsTitleDetail;
import com.anycommerce.model.dto.TermsTitleResponse;
import com.anycommerce.model.entity.Terms;
import com.anycommerce.model.entity.TermsId;
import com.anycommerce.repository.TermsRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TermsService {

    private final TermsRepository termsRepository;


    // 1. (화면에 보여주기 위한) 최신 활성화된 약관 조회
    public TermsTitleResponse getLatestTermsTitles() {
        // 최신 약관 목록을 가져와 TermsTitleDetail 객체로 변환
        List<TermsTitleDetail> termsList = termsRepository.findLatestActiveTerms().stream()
                .map(term -> new TermsTitleDetail(term.getId().getTitle(), term.isRequired()))
                .toList();

        // TermsTitleResponse 객체 생성 및 반환
        return new TermsTitleResponse(termsList);
    }


    // 2. 특정 약관 내용 조회
    public String getTermsContentById(TermsId id) {
        return termsRepository.findById(id)
                .map(Terms::getContent)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.NOT_FOUND));
    }
}
