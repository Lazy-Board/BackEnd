package com.example.lazier.user.service;

import com.example.lazier.user.dto.TokenDto;
import com.example.lazier.user.entity.LazierUser;
import com.example.lazier.user.exception.NotFoundMemberException;
import com.example.lazier.user.exception.NotMatchMemberException;
import com.example.lazier.user.model.UserLoginInput;
import com.example.lazier.user.repository.UserRepository;
import com.example.lazier.user.security.JwtTokenProvider;
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
	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final PasswordEncoder passwordEncoder;

	public TokenDto createAccessToken(UserLoginInput userLogin) {

		LazierUser lazierUser = userRepository.findByUserEmail(userLogin.getUserEmail())
			.orElseThrow(() -> new NotFoundMemberException("사용자 정보를 찾을 수 없습니다."));

		if (!passwordEncoder.matches(userLogin.getPassword(), lazierUser.getPassword())) {
			throw new NotMatchMemberException("아이디 혹은 비밀번호가 잘못되었습니다.");
		}

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(lazierUser.getUserId(), userLogin.getPassword(),
				lazierUser.getAuthorities());

		Authentication authentication =
			authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		TokenDto tokenDTO = jwtTokenProvider.createAccessToken(authentication);

		return tokenDTO;
	}
}
