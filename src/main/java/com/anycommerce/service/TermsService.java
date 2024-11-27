package com.anycommerce.service;

import com.anycommerce.model.dto.TermsTitleResponse;
import com.anycommerce.model.entity.Terms;
import com.anycommerce.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TermsService {

    private final TermsRepository termsRepository;


    // 1. 최신 약관 제목과 필수 여부만 반환 + 신규로 발생가능한 약관 처리
    public List<TermsTitleResponse> getLatestTermsTitles() {

        List<Terms> latestTerms = termsRepository.findLatestTerms();

        // 전체 약관 조회
        List<Terms> allTerms = termsRepository.findAll();

        // 최신 약관에서 신규로 추가된 약관 필터링
        List<Terms> newTerms = allTerms.stream()
                                .filter(term -> latestTerms.stream()
                                        .noneMatch(latest -> latest.getTitle().equals(term.getTitle())))
                                        .toList();

        // 기존 최신 약관과 신규 약관 병합
        latestTerms.addAll(newTerms);

        // List<Term> -> List<TermTitleResponse> 로 변환하기.
        return latestTerms.stream()
                .map(term -> new TermsTitleResponse(term.getTitle(), term.isRequired()))
                .toList();

    }


    // 2. 특정 약관 내용 조회
    public String getTermsContentById(Long id) {
        Terms terms = termsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("약관을 찾을 수 없습니다."));
        return terms.getContent();
    }
}
