package com.anycommerce.service;

import com.anycommerce.model.dto.SignUpRequestDto;
import com.anycommerce.model.entity.Address;
import com.anycommerce.model.entity.User;
import com.anycommerce.repository.UserRepository;
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

    public void save(SignUpRequestDto dto){
        Address address = new Address(dto.getAddress().getZipcode(), dto.getAddress().getStreetAdr(), dto.getAddress().getDetailAdr());

        User user = User.builder()
                .userId(dto.getUserId())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .address(address)
                .build();

        userRepository.save(user);

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


