package com.anycommerce.service;

import com.anycommerce.model.dto.TermsTitleResponse;
import com.anycommerce.model.entity.Terms;
import com.anycommerce.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TermsService {

    private final TermsRepository termsRepository;


    // 1. 최신 약관 제목과 필수 여부만 반환 + 신규로 발생가능한 약관 처리 + 활성화된 약관들만 수집
    public List<TermsTitleResponse> getLatestTermsTitles() {

        // 최신 활성화된 약관 조회
        List<Terms> latestActiveTerms = termsRepository.findLatestActiveTerms();

        // 전체 로직중에서 비활성화된것은 제외하고 찾아온다.
        List<Terms> allTerms = termsRepository.findAll().stream()
                .filter(Terms::isActive)
                .toList();

        // 신규 추가된 약관 -> 전체 약관중 최신 활성화된 것에서 전체 약관이랑 안 겹치는 것
        List<Terms> newTerms = allTerms.stream()
                .filter(term -> latestActiveTerms.stream()
                        .noneMatch(latest -> latest.getTitle().equals(term.getTitle())))
                .toList();

        // 두개 합쳐서
        latestActiveTerms.addAll(newTerms);

        // Dto에 매핑해서 리턴
        return latestActiveTerms.stream()
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
