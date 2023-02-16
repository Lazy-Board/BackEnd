package com.example.lazier.user.service;

import com.example.lazier.user.config.JwtTokenProvider;
import com.example.lazier.user.dto.GoogleUserDto;
import com.example.lazier.user.dto.OAuthTokenDto;
import com.example.lazier.user.dto.TokenDto;
import com.example.lazier.user.entity.LazierUser;
import com.example.lazier.user.enums.UserStatus;
import com.example.lazier.user.exception.InvalidAccessException;
import com.example.lazier.user.repository.UserRepository;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisService redisService;
	private final InMemoryClientRegistrationRepository inMemoryClientRegistrationRepository;

	@Transactional
	public LazierUser getUser(String provider, String code) {
		ClientRegistration clientRegistration = inMemoryClientRegistrationRepository.findByRegistrationId(provider.toLowerCase());
		OAuthTokenDto oAuthTokenDto = getToken(clientRegistration, code); //google 연동 토큰, 이 토큰으로 유저 정보 받아옴

		return saveUserInfo(provider.toLowerCase(), oAuthTokenDto, clientRegistration);
	}

	private OAuthTokenDto getToken(ClientRegistration provider, String code) {
		return WebClient.create()
			.post()
			.uri(provider.getProviderDetails().getTokenUri())
			.headers(header -> {
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})

			.bodyValue(tokenRequest(provider, code))
			.retrieve()
			.bodyToMono(OAuthTokenDto.class)
			.block();
	}

	private MultiValueMap<String, String> tokenRequest(ClientRegistration provider, String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		formData.add("redirect_uri", provider.getRedirectUri());
		formData.add("client_secret", provider.getClientSecret());
		formData.add("client_id", provider.getClientId());

		return formData;
	}

	private LazierUser saveUserInfo(String providerName, OAuthTokenDto oAuthTokenDto,
								ClientRegistration provider) {

		Map<String, Object> attributes = getUserAttribute(provider, oAuthTokenDto); //user 정보 담을 객체
		GoogleUserDto googleUserDto;
		String oauthNickName = null;
		String oauthName = null;

		if (providerName.equals("google")) {
			googleUserDto = new GoogleUserDto(attributes);
			oauthName = googleUserDto.getName();
		} else {
			log.info("잘못된 접근입니다.");
			throw new InvalidAccessException("잘못된 접근입니다.");
		}

		String oauthProvider = googleUserDto.getProvider(); //google 타입
		String oauthProviderId = googleUserDto.getProviderId(); //sub (google 통신할 때 사용하는 키)
		String oauthEmail = googleUserDto.getUserEmail();

		Optional<LazierUser> lazierUser = userRepository.findByOauthId(oauthProviderId);

		if (lazierUser.isEmpty()) {
			LazierUser member = LazierUser.builder()
				.userEmail(oauthEmail)
				.userName(oauthName)
				.oauthId(oauthProviderId)
				.createdAt(LocalDateTime.now())
				.userStatus(UserStatus.STATUS_ACTIVE.getUserStatus())
				.socialType(oauthProvider)
				.dataStatus("NEED_DATA") //삭제할까 말까
				.build();
			return userRepository.save(member); //없으면 저장
		} else {
			return lazierUser.get(); //있으면 리턴
		}
	}

	private Map<String, Object> getUserAttribute(ClientRegistration provider, OAuthTokenDto oAuthTokenDto) {
		return WebClient.create()
			.get()
			.uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
			.headers(header -> header.setBearerAuth(oAuthTokenDto.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();
	}

	public TokenDto loginResult(LazierUser lazierUser) {
		return getMemberLoginResponseDto(lazierUser);
	}

	private TokenDto getMemberLoginResponseDto(LazierUser lazierUser) {
		TokenDto tokenDto = jwtTokenProvider.createAccessToken(String.valueOf(lazierUser.getUserId()));

		redisService.setValues(tokenDto.getRefreshToken()); //tokenDto에서 refresh token은 redis에 저장
		return tokenDto; //tokenDto 넘기기
	}


}
