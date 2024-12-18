package com.anycommerce.service;

import com.anycommerce.config.EncryptionUtil;
import com.anycommerce.exception.CustomBusinessException;
import com.anycommerce.exception.ErrorCode;
import com.anycommerce.model.dto.SignUpRequestDto;
import com.anycommerce.model.entity.Terms;
import com.anycommerce.model.entity.User;
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
    private final VerificationCodeService verificationCodeService;



    @Transactional
    public void registerUser(SignUpRequestDto dto){
        // 1. ID 중복체크
        if (checkUserIdDuplicate(dto.getUserId())) {
            throw new CustomBusinessException(ErrorCode.DUPLICATE_USER_ID);
        }

        // 2. Email 중복체크
        if (checkEmailDuplicate(dto.getEmail())) {
            throw new CustomBusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 3. Phone 중복체크
        if (checkPhoneNumberDuplicate(dto.getPhoneNumber())) {
            throw new CustomBusinessException(ErrorCode.INVALID_PHONE_NUMBER);
        }

        // 4. 필수 약관 동의 확인
        if (!hasAgreedToRequiredTerms(dto.getAgreement())) {
            throw new CustomBusinessException(ErrorCode.NOT_AGREED_REQUIRED_TERMS);
        }

        // 5. 전화번호 확인
        String encryptedPhoneNumber = verificationCodeService.encryptVerifiedPhoneNumber(dto.getPhoneNumber());
        String decryptedPhoneNumber = encryptionUtil.decrypt(encryptedPhoneNumber);
        if (!decryptedPhoneNumber.equals(dto.getPhoneNumber())) {
            throw new CustomBusinessException(ErrorCode.INVALID_PHONE_NUMBER);
        }


        // 6. 회원가입 처리
        User user = User.builder()
                .userId(dto.getUserId())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .zipcode(dto.getZipcode())
                .streetAddress(dto.getStreetAddress())
                .detailAddress(dto.getDetailAddress())
                .build();

        userRepository.save(user);

        // 8. 인증 데이터 삭제
        verificationCodeRepository.deleteByPhoneNumber(dto.getPhoneNumber());
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


    /**
     *  유저 아이디로 찾기
     * @param userId
     */
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomBusinessException(ErrorCode.INVALID_USER));
    }

}


