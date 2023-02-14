package com.example.lazier.user.security;

import com.example.lazier.user.dto.AccessTokenDto;
import com.example.lazier.user.entity.RefreshToken;
import com.example.lazier.user.exception.InvalidTokenException;
import com.example.lazier.user.exception.UnauthorizedRefreshTokenException;
import com.example.lazier.user.service.RedisService;
import io.jsonwebtoken.JwtException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    public String getRefreshToken(String userId) {
        return redisService.getValues(userId);
    }

    public AccessTokenDto validateRefreshToken(HttpServletRequest request) { //refresh 유효성 검사

        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String userId = jwtTokenProvider.getUserPk(refreshToken);
        String redisToken = getRefreshToken(userId);

        if (!redisToken.equals(refreshToken)) {
            throw new InvalidTokenException("유효하지 않는 토큰입니다.");
        }

        RefreshToken token = RefreshToken.builder()
            .refreshToken(refreshToken)
            .keyId(userId)
            .build();

        String createdAccessToken = "";
        try {
            createdAccessToken = jwtTokenProvider.validateRefreshToken(token);
        } catch (JwtException e) {
            throw new UnauthorizedRefreshTokenException(e.getMessage());
        }
        return createdRefreshJson(createdAccessToken);
    }

    //token -> dto
    public AccessTokenDto createdRefreshJson(String createdAcessToken) {

        return AccessTokenDto.builder()
                .accessToken(createdAcessToken)
                .grantType("Bearer")
                .build();

    }

}
