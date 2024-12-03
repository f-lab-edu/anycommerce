package com.anycommerce.service;

import com.anycommerce.config.EncryptionUtil;
import com.anycommerce.model.dto.SignUpRequestDto;
import com.anycommerce.model.entity.Terms;
import com.anycommerce.model.entity.User;
import com.anycommerce.model.entity.VerificationCode;
import com.anycommerce.repository.TermsRepository;
import com.anycommerce.repository.UserRepository;
import com.anycommerce.repository.VerificationCodeRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Builder
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TermsRepository termsRepository;
    private final EncryptionUtil encryptionUtil;
    private final VerificationCodeRepository verificationCodeRepository;



    @Transactional
    public void registerUser(SignUpRequestDto dto){
        // 1. ID 중복체크
        if (checkUserIdDuplicate(dto.getUserId())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        // 2. Email 중복체크
        if (checkEmailDuplicate(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        // 3. Phone 중복체크
        if (checkPhoneNumberDuplicate(dto.getPhoneNumber())) {
            throw new IllegalArgumentException("이미 등록된 전화번호입니다.");
        }

        // 4. 필수 약관 동의 확인
        if (!hasAgreedToRequiredTerms(dto.getAgreement())) {
            throw new IllegalArgumentException("필수 약관에 동의하지 않았습니다.");
        }

        // 5. 인증된 전화번호 확인
        String encryptedPhoneNumber = encryptionUtil.encrypt(dto.getPhoneNumber());
        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(encryptedPhoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 전화번호는 인증되지 않았습니다."));

        // 6. 복호화된 전화번호 비교
        String decryptedPhoneNumber = encryptionUtil.decrypt(verificationCode.getPhoneNumber());
        if (!decryptedPhoneNumber.equals(dto.getPhoneNumber())) {
            throw new IllegalArgumentException("전화번호가 인증된 번호와 일치하지 않습니다.");
        }

        // 7. 회원가입 처리
        User user = User.builder()
                .userId(dto.getUserId())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phoneNumber(encryptedPhoneNumber)
                .zipcode(dto.getZipcode())
                .streetAddress(dto.getStreetAddress())
                .detailAddress(dto.getDetailAddress())
                .build();

        userRepository.save(user);

        // 8. 인증 데이터 삭제
        verificationCodeRepository.delete(verificationCode);
    }



    // Id 중복체크
    public boolean checkUserIdDuplicate(String userId) {
        return userRepository.existsByUserId(userId);
    }

    // Email 중복체크
    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    // 전화번호 중복체크
    public boolean checkPhoneNumberDuplicate(String phoneNumber) {
        String encryptedPhoneNumber = encryptionUtil.encrypt(phoneNumber);
        return userRepository.existsByPhoneNumber(encryptedPhoneNumber);
    }

    /**
     * 필수 약관 동의 여부 확인
     * @param agreement 사용자 동의 데이터 (약관 ID와 동의 여부)
     * @return 모든 필수 약관에 동의했는지 여부
     */
    public boolean hasAgreedToRequiredTerms(Map<Long, Boolean> agreement) {
        // 최신 버전 + 활성화된 필수 약관 조회
        List<Terms> requiredTerms = termsRepository.findLatestRequiredTerms();

        // 필수 약관 동의 여부 확인
        for (Terms term : requiredTerms) {
            if (!agreement.getOrDefault(term.getId(), false)) {
                return false; // 하나라도 동의하지 않았다면 false 반환
            }
        }

        return true; // 모든 필수 약관에 동의했다면 true 반환
    }

}


