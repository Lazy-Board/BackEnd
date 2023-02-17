package com.example.lazier.controller;

import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.service.user.JwtService;
import com.example.lazier.dto.user.TokenDto;
import com.example.lazier.dto.user.UserLoginInput;
import com.example.lazier.dto.user.UserSignupInput;
import com.example.lazier.service.user.JoinService;
import com.example.lazier.service.user.CreateTokenService;
import com.example.lazier.service.user.OAuthService;
import com.example.lazier.service.user.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final CreateTokenService createTokenService;
    private final JoinService joinService;
    private final JwtService jwtService;
    private final RedisService redisService;
    private final OAuthService oAuthService;

    @PostMapping("/signup")
    public ResponseEntity<?> join(@RequestBody @Valid UserSignupInput request) {

        return new ResponseEntity<>(joinService.signUp(request), HttpStatus.OK);
    }

    @GetMapping("/email-auth")
    public ResponseEntity<?> emailAuth(@RequestParam(value = "uuid") String uuid) {
        joinService.emailAuth(uuid);

        return ResponseEntity.ok().body("회원가입 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid UserLoginInput userLogin) {

        TokenDto tokenDto = createTokenService.createAccessToken(userLogin);
        redisService.setValues(tokenDto.getRefreshToken());

        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping ("/login/oauth2/code/{provider}")
    public ResponseEntity<?> loginGoogle(@PathVariable String provider, @RequestParam String code) {
        LazierUser lazierUser = oAuthService.getUser(provider, code);
        return ResponseEntity.ok(oAuthService.loginResult(lazierUser));
    }


    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        redisService.delValues(request);

        return ResponseEntity.ok().body("로그아웃 완료");
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request) { //Authentication 헤더로 "Bearer " + 토큰

        return request.getAttribute("userId").toString();
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> validateRefreshToken(HttpServletRequest request) { //RefreshToken 헤더로 "Bearer " + 토큰

        return new ResponseEntity<>(jwtService.validateRefreshToken(request), HttpStatus.OK);
    }
}
