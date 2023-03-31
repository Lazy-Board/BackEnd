package com.example.lazier.service.user;

import com.example.lazier.component.MailComponents;
import com.example.lazier.dto.user.SaveModuleRequestDto;
import com.example.lazier.dto.user.SignUpRequestDto;
import com.example.lazier.dto.user.SignUpResponseDto;
import com.example.lazier.exception.user.FailedSignUpException;
import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.persist.entity.module.ModuleYn;
import com.example.lazier.persist.repository.MemberRepository;
import com.example.lazier.persist.repository.ModuleYnRepository;
import com.example.lazier.service.module.NewsUserService;
import com.example.lazier.service.module.UserExchangeService;
import com.example.lazier.service.module.UserStockService;
import com.example.lazier.type.MemberStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModuleYnRepository moduleYnRepository;
    private final MailComponents mailComponents;
    private final PasswordEncoder passwordEncoder;
    private final UserExchangeService userExchangeService;
    private final UserStockService userstockService;
    private final NewsUserService newsUserService;

    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {

        boolean existsEmail = memberRepository.existsByUserEmail(signUpRequestDto.getUserEmail());
        if (existsEmail) { throw new FailedSignUpException("이미 가입된 이메일입니다."); }

        String uuid = UUID.randomUUID().toString();

        LazierUser lazierUser = memberRepository.save(LazierUser.builder()
            .userEmail(signUpRequestDto.getUserEmail())
            .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
            .name(signUpRequestDto.getUserName())
            .phoneNumber(signUpRequestDto.getPhoneNumber())
            .socialType("no-social")
            .createdAt(LocalDateTime.now())
            .userStatus(MemberStatus.STATUS_READY.getUserStatus())
            .emailAuthKey(uuid)
            .emailAuthYn(false)
            .build());

        ModuleYn moduleYn = ModuleYn.builder()
            .lazierUser(lazierUser)
            .moduleCode(false)
            .build();
        moduleYnRepository.save(moduleYn);

        String email = lazierUser.getUserEmail();
        String title = "Lazier 가입을 축하드립니다.";
        String contents = "아래 링크를 클릭하여 가입을 완료하세요." +
            "<p>" +
            "<a target='_blank' href='http://3.34.73.141:8080/user/email-auth?uuid=" + uuid + "'>가입완료</a>" +
            "</p>";

        boolean sendEmail = mailComponents.sendEmail(email, title, contents);
        if (!sendEmail) { throw new FailedSignUpException("메일 전송에 실패하였습니다."); }

        return SignUpResponseDto.builder() //for test
            .uuid(uuid)
            .message("이메일 인증을 완료하세요")
            .build();
    }

    @Transactional
    public void saveModule(HttpServletRequest request, SaveModuleRequestDto saveModuleRequestDto) {

        String userId = request.getAttribute("userId").toString();

        LazierUser lazierUser = memberRepository.findByUserId(Long.valueOf(userId))
            .orElseThrow(() -> new FailedSignUpException("회원가입을 진행하세요."));

        ModuleYn moduleYn = moduleYnRepository.findByUserId(lazierUser.getUserId());
        ModuleYn.save(moduleYn, saveModuleRequestDto); //모듈 저장

        //환율, 주식, 뉴스 추가
        if (saveModuleRequestDto.isNewsYn()) {
            newsUserService.add(userId);
        }

        if (saveModuleRequestDto.isStockYn()) {
            userstockService.add(userId);
        }

        if (saveModuleRequestDto.isExchangeYn()) {
            userExchangeService.add(userId);
        }
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
