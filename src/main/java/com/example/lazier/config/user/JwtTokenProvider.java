package com.example.lazier.config.user;

import com.example.lazier.dto.user.TokenResponseDto;
import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.persist.entity.module.ModuleYn;
import com.example.lazier.persist.entity.module.RefreshToken;
import com.example.lazier.persist.repository.ModuleYnRepository;
import com.example.lazier.service.user.LoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${jwt.secret}")
	private String secretKey;

//	@Value("${jwt.token-validity-in-seconds}")
//	private long accessTokenValidTime; 								//30 * 60 * 1000L 30분

//	@Value("${jwt.refreshToken-validity-in-seconds}")
//	private long refreshTokenValidTime; 							//24 * 60 * 60 * 1000L 1일

	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER_TYPE = "Bearer";
	public static final String REFRESH_TOKEN = "RefreshToken";

	private long accessTokenValidTime = 24 * 60 * 60 * 1000L; 		//(test)
	private long refreshTokenValidTime = 5 * 60 * 1000L; 			//(test)

	private final LoginService loginService;
	private final ModuleYnRepository moduleYnRepository;


	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder()
			.encodeToString(secretKey.getBytes());
	}

	public String getUserPk(String token) {
		try {
			String username = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject(); //username
			log.info("getUserPk 아이디" + username);
			return username;
		} catch (Exception e) {
			throw new JwtException(e.getMessage());
		}
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = loginService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "",
			userDetails.getAuthorities());
	}

	public TokenResponseDto createAccessToken(String userId) {

		ModuleYn moduleYn = moduleYnRepository.findAllByUserId(Long.valueOf(userId));

		Claims claims = Jwts.claims().setSubject(userId);
		Date now = new Date();

		String accessToken = Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();

		LocalDateTime expiredTime = LocalDateTime.now().plusSeconds(accessTokenValidTime / 1000);

		String refreshToken = Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidTime))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();

		return TokenResponseDto.builder()
			.accessToken(accessToken)
			.grantType(BEARER_TYPE)
			.refreshToken(refreshToken)
			.expiredTime(expiredTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
			.moduleCode(moduleYn.isModuleCode())
			.build();
	}

	public String resolveToken(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		if (!ObjectUtils.isEmpty(token) && token.toLowerCase()
			.startsWith(BEARER_TYPE.toLowerCase())) {
			return token.substring(BEARER_TYPE.length()).trim();
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			log.warn("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.warn("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.warn("JWT 토큰이 잘못되었습니다.");
		}
		throw new JwtException("만료된 JWT 토큰입니다");
	}

	public String recreationAccessToken(String userId) {

		Claims claims = Jwts.claims().setSubject(userId);
		Date now = new Date();

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidTime))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public String resolveRefreshToken(HttpServletRequest request) {
		String token = request.getHeader(REFRESH_TOKEN);
		log.info("리프레시 토큰: " + request.getHeader(REFRESH_TOKEN));
		if (!ObjectUtils.isEmpty(token) && token.toLowerCase()
			.startsWith(BEARER_TYPE.toLowerCase())) {
			return token.substring(BEARER_TYPE.length()).trim();
		}
		return null;
	}

	public String validateRefreshToken(RefreshToken token) {
		String refreshToken = token.getRefreshToken();

		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey)
				.parseClaimsJws(refreshToken);
			if (!claims.getBody().getExpiration().before(new Date())) {
				return recreationAccessToken(claims.getBody().getSubject());
			}
		} catch (Exception e) {
			throw new JwtException(e.getMessage());
		}
		return null;
	}


}
