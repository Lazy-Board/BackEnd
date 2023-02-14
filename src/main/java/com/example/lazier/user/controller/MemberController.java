package com.example.lazier.user.controller;

import com.example.lazier.user.dto.AccessTokenDto;
import com.example.lazier.user.dto.TokenDto;
import com.example.lazier.user.model.UserLoginInput;
import com.example.lazier.user.model.UserSignupInput;
import com.example.lazier.user.security.JwtService;
import com.example.lazier.user.service.JoinService;
import com.example.lazier.user.service.CreateTokenService;
import com.example.lazier.user.service.RedisService;
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

    private final JoinService joinService;
    private final CreateTokenService createTokenService;
    private final JwtService jwtService;
    private final RedisService redisService;

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

        TokenDto tokenDTO = createTokenService.createAccessToken(userLogin);
        redisService.setValues(tokenDTO.getRefreshToken());

        return ResponseEntity.ok(tokenDTO);
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
        AccessTokenDto accessTokenDTO = jwtService.validateRefreshToken(request);

        return new ResponseEntity<>(accessTokenDTO, HttpStatus.OK);
    }
}
