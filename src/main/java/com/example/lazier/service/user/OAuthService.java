package com.example.lazier.service.user;

import com.example.lazier.config.user.JwtTokenProvider;
import com.example.lazier.dto.user.GoogleUserDto;
import com.example.lazier.dto.user.OAuthTokenDto;
import com.example.lazier.dto.user.TokenDto;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.type.UserStatus;
import com.example.lazier.exception.user.InvalidAccessException;
import com.example.lazier.persist.repository.UserRepository;
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
		OAuthTokenDto oAuthTokenDto = getToken(clientRegistration, code);

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

		Map<String, Object> attributes = getUserAttribute(provider, oAuthTokenDto); //user 담을 객체
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

		String oauthProvider = googleUserDto.getProvider(); //타입
		String oauthProviderId = googleUserDto.getProviderId(); //sub
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
				.build();
			return userRepository.save(member); //없으면 저장
		} else {
			return lazierUser.get(); //있으면 리턴
		}
	}

	private Map<String, Object> getUserAttribute(ClientRegistration provider,
		OAuthTokenDto oAuthTokenDto) {
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
		TokenDto tokenDto = jwtTokenProvider.createAccessToken(
			String.valueOf(lazierUser.getUserId()));

		redisService.setValues(tokenDto.getRefreshToken()); //tokenDto에서 refresh token은 redis에 저장
		return tokenDto; //tokenDto 넘기기
	}


}
