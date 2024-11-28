package com.anycommerce.service;

import com.anycommerce.config.EncryptionUtil;
import com.anycommerce.model.dto.SignUpRequestDto;
import com.anycommerce.model.entity.User;
import com.anycommerce.model.entity.VerificationCode;
import com.anycommerce.repository.UserRepository;
import com.anycommerce.repository.VerificationCodeRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Builder
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EncryptionUtil encryptionUtil;
    private final VerificationCodeRepository verificationCodeRepository;

    @Transactional
    public void registerUser(SignUpRequestDto dto){

        // 암호화된 전화번호로 인증된 데이터 조회
        String encryptedPhoneNumber = encryptionUtil.encrypt(dto.getPhoneNumber());
        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(encryptedPhoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 전화번호는 인증되지 않았습니다."));

        // 복호화된 전화번호와 사용자가 입력한 전화번호 비교
        String decryptedPhoneNumber;
        try {
            decryptedPhoneNumber = encryptionUtil.decrypt(verificationCode.getPhoneNumber());
            if (!decryptedPhoneNumber.equals(dto.getPhoneNumber())) {
                throw new IllegalArgumentException("전화번호가 인증된 번호와 일치하지 않습니다.");
            }
        } catch (Exception e) {
            throw new IllegalStateException("전화번호 복호화에 실패했습니다.");
        }

        // 회원 가입 처리 (User 저장)
        User user = User.builder()
                .userId(dto.getUserId())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .build();

        userRepository.save(user);

        // 회원가입 완료 후 인증 데이터 삭제
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

}


