package com.example.lazier.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class LazierUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 100, unique = true)
    private String userEmail;

    private String password;

    private String userName;

    private String nickName;

    private String phoneNumber;

    private LocalDateTime createdAt;

    private String userStatus;

    private boolean socialStatus;

    //이메일 인증
    private String emailAuthKey;
    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;

    //비밀번호 찾기
    private String resetPasswordKey;
    private boolean resetPasswordKeyAuthYn;
    private LocalDateTime resetPasswordLimitDt; //인증키 유효기간

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
