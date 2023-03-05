package com.example.lazier.service.user;

import com.example.lazier.config.user.JwtTokenProvider;
import com.example.lazier.dto.user.TokenResponseDto;
import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.exception.user.NotFoundMemberException;
import com.example.lazier.exception.user.NotMatchMemberException;
import com.example.lazier.dto.user.LoginRequestDto;
import com.example.lazier.persist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CreateTokenService {

	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final PasswordEncoder passwordEncoder;

	public TokenResponseDto createAccessToken(LoginRequestDto userLogin) {

		LazierUser lazierUser = memberRepository.findByUserEmail(userLogin.getUserEmail())
			.orElseThrow(() -> new NotFoundMemberException("사용자 정보를 찾을 수 없습니다."));

		if (!passwordEncoder.matches(userLogin.getPassword(), lazierUser.getPassword())) {
			throw new NotMatchMemberException("아이디 혹은 비밀번호가 잘못되었습니다.");
		}

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(lazierUser.getUserId(), userLogin.getPassword(),
				lazierUser.getAuthorities());

		Authentication authentication =
			authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		TokenResponseDto tokenDTO = jwtTokenProvider.createAccessToken(authentication.getName());

		return tokenDTO;
	}
}
