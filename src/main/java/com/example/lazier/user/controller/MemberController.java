package com.example.lazier.user.controller;

import com.example.lazier.user.dto.AccessTokenDTO;
import com.example.lazier.user.dto.TokenDTO;
import com.example.lazier.user.model.UserLogin;
import com.example.lazier.user.model.UserSignUp;
import com.example.lazier.user.security.JwtService;
import com.example.lazier.user.service.join.JoinService;
import com.example.lazier.user.service.login.CreateTokenService;
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
    private final JwtService jwtService;
    private final CreateTokenService createTokenService;

    @PostMapping("/signUp")
    public ResponseEntity<?> join(@RequestBody @Valid UserSignUp request) {

        return new ResponseEntity<>(joinService.signUp(request), HttpStatus.OK);
    }

    @GetMapping("/email-auth")
    public ResponseEntity<?> emailAuth(@RequestParam(value = "uuid") String uuid) {
        joinService.emailAuth(uuid);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid UserLogin userLogin) {

        TokenDTO tokenDTO = createTokenService.createAccessToken(userLogin);
        jwtService.saveRefreshToken(tokenDTO); //db에 refresh token 저장

        return ResponseEntity.ok(tokenDTO);
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        return (String) request.getAttribute("userId");
    }
}
