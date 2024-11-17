package com.anycommerce.service;

import com.anycommerce.model.dto.SignUpRequestDto;
import com.anycommerce.model.entity.Address;
import com.anycommerce.model.entity.User;
import com.anycommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(SignUpRequestDto dto){
        Address address = new Address(dto.getAddress().getZipcode(), dto.getAddress().getStreetAdr(), dto.getAddress().getDetailAdr());
//        return userRepository.save(User.builder()
//                .userId(dto.getUserId())
//                .password((bCryptPasswordEncoder.encode(dto.getPassword())))
//                .username(dto.getUsername())
//                .address(address)
//                .email(dto.getEmail())
//                .phoneNumber(dto.getPhoneNumber())
//                .build()).getId();


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


}
