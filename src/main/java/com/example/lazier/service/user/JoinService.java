package com.example.lazier.service.user;

import com.example.lazier.component.MailComponents;
import com.example.lazier.dto.user.SignUpRequestDto;
import com.example.lazier.dto.user.UniqueCodeDto;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.type.MemberStatus;
import com.example.lazier.exception.user.FailedSignUpException;
import com.example.lazier.persist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;
    private final PasswordEncoder passwordEncoder;

    public UniqueCodeDto signUp(SignUpRequestDto signUpRequestDto) {

        boolean existsEmail = memberRepository.existsByUserEmail(signUpRequestDto.getUserEmail());
        if (existsEmail) { throw new FailedSignUpException("이미 가입된 이메일입니다."); }

        String uuid = UUID.randomUUID().toString();

        LazierUser lazierUser = LazierUser.builder()
                .userEmail(signUpRequestDto.getUserEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .userName(signUpRequestDto.getUserName())
                .phoneNumber(signUpRequestDto.getPhoneNumber())
                .socialType("no-social")
                .createdAt(LocalDateTime.now())
                .userStatus(MemberStatus.STATUS_READY.getUserStatus())
                .emailAuthKey(uuid)
                .emailAuthYn(false)
                .build();

        memberRepository.save(lazierUser);

        String email = signUpRequestDto.getUserEmail();
        String title = "Lazier 가입을 축하드립니다.";
        String contents = "아래 링크를 클릭하여 가입을 완료하세요." +
                "<p>" +
                "<a target='_blank' href='http://3.35.129.231:8080/user/email-auth?uuid=" + uuid + "'>가입완료</a>" +
                "</p>";

        boolean sendEmail = mailComponents.sendEmail(email, title, contents);
        if (!sendEmail) { throw new FailedSignUpException("메일 전송에 실패하였습니다."); }

        return UniqueCodeDto.builder()
                .uuid(uuid)
                .build();
    }

    public void emailAuth(String uuid) {
        LazierUser lazierUser = memberRepository.findByEmailAuthKey(uuid)
                .orElseThrow(() -> new FailedSignUpException("회원가입을 진행하세요."));

        lazierUser.setEmailAuthYn(true);
        lazierUser.setEmailAuthDt(LocalDateTime.now());
        lazierUser.setUserStatus(MemberStatus.STATUS_ACTIVE.toString());
        memberRepository.save(lazierUser);
    }
}
