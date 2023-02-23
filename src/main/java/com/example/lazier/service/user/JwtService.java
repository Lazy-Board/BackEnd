package com.example.lazier.service.user;

import com.example.lazier.config.user.JwtTokenProvider;
import com.example.lazier.dto.user.AccessTokenResponseDto;
import com.example.lazier.persist.entity.user.RefreshToken;
import com.example.lazier.exception.user.InvalidTokenException;
import com.example.lazier.exception.user.UnauthorizedRefreshTokenException;
import io.jsonwebtoken.JwtException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    private final RedisTemplate<String, String> redisTemplate;

    private long refreshTokenValidTime = 5 * 60 * 1000L;

    public String getRefreshToken(String userId) {
        return redisService.getValues(userId);
    }

    public AccessTokenResponseDto validateRefreshToken(HttpServletRequest request) { //refresh 유효성 검사

        String userId;
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        try {
            userId = jwtTokenProvider.getUserPk(refreshToken);
        } catch (Exception e) {
            throw new UnauthorizedRefreshTokenException(e.getMessage());
        }

        String redisToken = getRefreshToken(userId);

        if (redisToken == null || redisToken.equals("")) {
            throw new InvalidTokenException("이미 재발급되었습니다.");
        }

        if (!redisToken.equals(refreshToken)) {
            throw new InvalidTokenException("유효하지 않는 토큰입니다.");
        }

        RefreshToken token = RefreshToken.builder()
            .refreshToken(refreshToken)
            .keyId(userId)
            .build();

        redisTemplate.delete(userId);
        String createdAccessToken = "";
        try {
            createdAccessToken = jwtTokenProvider.validateRefreshToken(token);
        } catch (JwtException e) {
            throw new UnauthorizedRefreshTokenException(e.getMessage());
        }
        return createdRefreshJson(createdAccessToken);
    }

    //token -> dto
    public AccessTokenResponseDto createdRefreshJson(String createdAcessToken) {

        LocalDateTime expiredTime = LocalDateTime.now().plusSeconds(refreshTokenValidTime / 1000);

        return AccessTokenResponseDto.builder()
                .accessToken(createdAcessToken)
                .grantType("Bearer")
                .expiredTime(expiredTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) //<- 얘
                .build();

    }

}
