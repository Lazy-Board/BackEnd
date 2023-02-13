package com.example.lazier.user.security;

import com.example.lazier.user.dto.AccessTokenDTO;
import com.example.lazier.user.dto.TokenDTO;
import com.example.lazier.user.entity.RefreshToken;
import com.example.lazier.user.exception.UnauthorizedRefreshTokenException;
import com.example.lazier.user.repository.RefreshTokenRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveRefreshToken(TokenDTO tokenDTO) {

        RefreshToken refreshToken = RefreshToken.builder()
                .keyId(tokenDTO.getKey())
                .refreshToken(tokenDTO.getRefreshToken())
                .build();

        String userId = refreshToken.getKeyId(); //1

        if (refreshTokenRepository.existsByKeyId(userId)) { //로그인 시 refresh token 새로 저장
            log.info("기존 refresh 토큰 삭제");
            refreshTokenRepository.deleteByKeyId(userId);
        }
        refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> getRefreshToken(String refreshToken) { //refresh token 추출
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public AccessTokenDTO validateRefreshToken(String refreshToken) { //refresh token 유효성 검사
        RefreshToken token = getRefreshToken(refreshToken).get();
        String createdAccessToken = "";
        try {
            createdAccessToken = jwtTokenProvider.validateRefreshToken(token);
        } catch (JwtException e) {
            throw new UnauthorizedRefreshTokenException(e.getMessage());
        }
        return createdRefreshJson(createdAccessToken);
    }

    //token -> dto
    public AccessTokenDTO createdRefreshJson(String createdAcessToken) {

        return AccessTokenDTO.builder()
                .accessToken(createdAcessToken)
                .grantType("Bearer")
                .build();

    }

}
