package com.example.lazier.service.user;

import com.example.lazier.config.user.JwtTokenProvider;
import com.example.lazier.dto.user.TokenDto;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.exception.user.NotFoundMemberException;
import com.example.lazier.exception.user.NotMatchMemberException;
import com.example.lazier.dto.user.UserLoginInput;
import com.example.lazier.persist.repository.UserRepository;
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

		TokenDto tokenDTO = jwtTokenProvider.createAccessToken(authentication.getName());

		return tokenDTO;
	}
}
