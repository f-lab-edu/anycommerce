package com.anycommerce.service;

import com.anycommerce.model.entity.Terms;
import com.anycommerce.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TermsService {

    private  final TermsRepository termsRepository;


    // 전체 약관 조회
    public List<Terms> getAllTerms() {

        List<Terms> terms = termsRepository.findAll();

        if(terms.isEmpty()) {
            throw new RuntimeException("DB에 약관이 하나도 없습니다.");
        }
        return terms;
    }


    // 특정 약관 조회
    public Terms getTermsById(Long id) {
        return termsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 약관이 존재하지 않습니다."));
    }


}
