package com.anycommerce.service;

import com.anycommerce.model.dto.TermsTitleResponse;
import com.anycommerce.model.entity.Terms;
import com.anycommerce.model.entity.TermsId;
import com.anycommerce.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class TermsService {

    private final TermsRepository termsRepository;


    // 1. (화면에 보여주기 위한) 최신 활성화된 약관 조회
    public List<TermsTitleResponse> getLatestTermsTitles() {
        // 최신 활성화된 약관 조회
        List<Terms> latestActiveTerms = termsRepository.findLatestActiveTerms();

        // Dto에 매핑해서 리턴
        return latestActiveTerms.stream()
                .map(term -> new TermsTitleResponse(term.getId().getTitle(), term.isRequired()))
                .toList();
    }


    // 2. 특정 약관 내용 조회
    public String getTermsContentById(TermsId id) {
        Terms terms = termsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("약관을 찾을 수 없습니다."));
        return terms.getContent();
    }
}
