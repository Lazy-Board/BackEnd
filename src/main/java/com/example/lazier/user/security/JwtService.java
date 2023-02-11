package com.example.lazier.user.security;

import com.example.lazier.user.dto.TokenDTO;
import com.example.lazier.user.entity.RefreshToken;
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

}
