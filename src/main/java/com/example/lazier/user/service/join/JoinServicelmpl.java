package com.example.lazier.user.service.join;

import com.example.lazier.user.component.MailComponents;
import com.example.lazier.user.dto.JoinDTO;
import com.example.lazier.user.entity.LazierUser;
import com.example.lazier.user.enums.UserRole;
import com.example.lazier.user.enums.UserStatus;
import com.example.lazier.user.exception.FailedSignUpException;
import com.example.lazier.user.model.UserSignUp;
import com.example.lazier.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JoinServicelmpl implements JoinService {

    private final UserRepository userRepository;
    private final MailComponents mailComponents;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JoinDTO signUp(UserSignUp request) {

        boolean existsEmail = userRepository.existsByUserEmail(request.getUserEmail());
        if (existsEmail) { throw new FailedSignUpException("이미 가입된 이메일입니다."); }

        String uuid = UUID.randomUUID().toString();

        LazierUser lazierUser = LazierUser.builder()
                .userEmail(request.getUserEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userName(request.getUserName())
                .nickName(request.getNickName())
                .phoneNumber(request.getPhoneNumber())
                .roles(Collections.singletonList(UserRole.USER.getUserRole()))

                .createdAt(LocalDateTime.now())
                .userStatus(UserStatus.STATUS_READY.toString())
                .emailAuthKey(uuid)
                .emailAuthYn(false)
                .build();

        userRepository.save(lazierUser);

        String email = request.getUserEmail();
        String title = "Lazier 가입을 축하드립니다.";
        String contents = "아래 링크를 클릭하여 가입을 완료하세요." +
                "<p>" +
                "<a target='_blank' href='http://localhost:8080/user/email-auth?uuid=" + uuid + "'>가입완료</a>" +
                "</p>";

        boolean sendEmail = mailComponents.sendEmail(email, title, contents);
        if (!sendEmail) { throw new FailedSignUpException("메일 전송에 실패하였습니다."); }

        return JoinDTO.builder()
                .uuid(uuid)
                .build();
    }

    @Override
    public void emailAuth(String uuid) {
        LazierUser lazierUser = userRepository.findByEmailAuthKey(uuid)
                .orElseThrow(() -> new FailedSignUpException("회원가입을 진행하세요."));

        lazierUser.setEmailAuthYn(true);
        lazierUser.setEmailAuthDt(LocalDateTime.now());
        lazierUser.setUserStatus(UserStatus.STATUS_ACTIVE.toString());
        userRepository.save(lazierUser);
    }
}
