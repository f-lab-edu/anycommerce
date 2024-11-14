package com.anycommerce.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "userId")
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Size(min= 2, max= 20, message = "아이디는 2자 이상, 20자 이하로 설정해야합니다.")
    private String userId;

    @Column(name = "userPw")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상, 20자 이하로 설정해야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "비밀번호는 8자 이상, 20자 이하이며, 하나 이상의 문자, 숫자 및 특수문자를 포함해야 합니다.")

    private String password;

    @Column(name = "userName")
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(min= 2, max= 20, message = "이름은 2자 이상, 20자 이하로 설정해야합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z_]{2,20}$", message = "이름은 특수문자를 제외한 2 ~ 20자이내여야 합니다.")
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Embedded
    private Address address;

    @Column(name = "phoneNumber")
    @NotBlank
    @Pattern(regexp = "^[0-9]{10,11}$", message = "핸드폰 번호는 10~11자리의 숫자만 가능합니다.")
    private String phoneNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Builder
    public User(Long id, String userId, String password, String username, String email, Address address, String phoneNumber, String auth) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

