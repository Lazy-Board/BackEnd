package com.example.lazier.user.security;

import com.example.lazier.user.dto.TokenDTO;
import com.example.lazier.user.service.login.LoginService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    //private long accessTokenVaildTime = 30 * 60 * 1000L; //30분
    //private long refreshTokenValidTime = 24 * 60 * 60 * 1000L; //1일

    private long accessTokenVaildTime =  1 * 60 * 1000L; //2분
    private long refreshTokenValidTime = 1 * 60 * 1000L; //2분

    private final LoginService loginService;

    @PostConstruct //종속성 주입이 완료된 후 실행되는 메서드 <- 호출되지 않아도 수행된다.
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); //객체 조기화, secretKey를 Base64로 인코딩한다.
    }

    public TokenDTO createAccessToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Claims claims = Jwts.claims().setSubject(authentication.getName()); //사용자 <- user_id
        claims.put("roles", authorities);
        Date now = new Date();

        //Access Token
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) //시간
                .setExpiration(new Date(now.getTime() + accessTokenVaildTime))
                .signWith(SignatureAlgorithm.HS256, secretKey) //암호화 알고리즘, signature에 들어갈 secretKey
                .compact();

        //Refresh Token
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenDTO.builder()
                .accessToken(accessToken)
                .grantType("Bearer")
                .refreshToken(refreshToken)
                .key(authentication.getName())
                .build();
    }


    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject(); //username
    }

}
