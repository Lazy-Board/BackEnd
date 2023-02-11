package com.example.lazier.user.service.login;

import com.example.lazier.user.entity.LazierUser;
import com.example.lazier.user.enums.UserStatus;
import com.example.lazier.user.exception.FailedLoginException;
import com.example.lazier.user.exception.NotFoundMemberException;
import com.example.lazier.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LazierUser lazierUser = userRepository.findByUserId(Long.valueOf(username))
                .orElseThrow(() -> new NotFoundMemberException("사용자 정보가 없습니다."));

        if (UserStatus.STATUS_READY.toString().equals(lazierUser.getUserStatus())) {
            throw new FailedLoginException("이메일 활성화 이후에 로그인 하세요.");
        }

        if (UserStatus.STATUS_WITHDRAW.toString().equals(lazierUser.getUserStatus())) {
            throw new FailedLoginException("탈퇴한 회원입니다.");
        }
        return lazierUser;
    }
}
