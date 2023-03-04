package com.example.lazier.service.user;

import com.example.lazier.config.user.JwtTokenProvider;
import com.example.lazier.dto.user.GoogleUserInfo;
import com.example.lazier.dto.user.OAuthTokenResponseDto;
import com.example.lazier.dto.user.TokenResponseDto;
import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.type.MemberStatus;
import com.example.lazier.exception.user.InvalidAccessException;
import com.example.lazier.persist.repository.MemberRepository;
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

	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final RedisService redisService;
	private final InMemoryClientRegistrationRepository inMemoryClientRegistrationRepository;

	@Transactional
	public LazierUser getUser(String code) {
		String provider = "google";
		ClientRegistration clientRegistration = inMemoryClientRegistrationRepository.findByRegistrationId(provider.toLowerCase());
		OAuthTokenResponseDto oAuthTokenResponseDto = getToken(clientRegistration, code);

		return saveUserInfo(provider.toLowerCase(), oAuthTokenResponseDto, clientRegistration);
	}

	private OAuthTokenResponseDto getToken(ClientRegistration provider, String code) {
		return WebClient.create()
			.post()
			.uri(provider.getProviderDetails().getTokenUri())
			.headers(header -> {
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})

			.bodyValue(tokenRequest(provider, code))
			.retrieve()
			.bodyToMono(OAuthTokenResponseDto.class)
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

	private LazierUser saveUserInfo(String providerName, OAuthTokenResponseDto oAuthTokenResponseDto,
		ClientRegistration provider) {

		Map<String, Object> attributes = getUserAttribute(provider, oAuthTokenResponseDto); //user 담을 객체
		GoogleUserInfo googleUserInfo;
		String oauthName = null;

		if (providerName.equals("google")) {
			googleUserInfo = new GoogleUserInfo(attributes);
			oauthName = googleUserInfo.getName();
		} else {
			log.info("잘못된 접근입니다.");
			throw new InvalidAccessException("잘못된 접근입니다.");
		}

		String oauthProvider = googleUserInfo.getProvider(); //타입
		String oauthProviderId = googleUserInfo.getProviderId(); //sub
		String oauthEmail = googleUserInfo.getUserEmail();

		Optional<LazierUser> lazierUser = memberRepository.findByOauthId(oauthProviderId);

		if (!lazierUser.isPresent()) {
			LazierUser member = LazierUser.builder()
				.userEmail(oauthEmail)
				.lazierName(oauthName)
				.oauthId(oauthProviderId)
				.createdAt(LocalDateTime.now())
				.userStatus(MemberStatus.STATUS_ACTIVE.getUserStatus())
				.socialType(oauthProvider)
				.build();
			log.info("member doesn't exist : " + member.getUserEmail());
			return memberRepository.save(member);
		} else {
			log.info("member exists : " + lazierUser.get().getUserEmail());
			return lazierUser.get();
		}
	}

	private Map<String, Object> getUserAttribute(ClientRegistration provider,
		OAuthTokenResponseDto oAuthTokenResponseDto) {
		return WebClient.create()
			.get()
			.uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
			.headers(header -> header.setBearerAuth(oAuthTokenResponseDto.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();
	}

	public TokenResponseDto loginResult(LazierUser lazierUser) {
		return getMemberLoginResponseDto(lazierUser);
	}

	private TokenResponseDto getMemberLoginResponseDto(LazierUser lazierUser) {
		TokenResponseDto tokenDto = jwtTokenProvider.createAccessToken(
			String.valueOf(lazierUser.getUserId()));
		log.info("google token : " + tokenDto.getAccessToken());
		log.info("google refreshToken : " + tokenDto.getRefreshToken());

		redisService.setValues(tokenDto.getRefreshToken());
		return tokenDto;
	}


}
