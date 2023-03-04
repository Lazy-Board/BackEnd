package com.example.lazier.controller;

import com.example.lazier.dto.user.SaveModuleRequestDto;
import com.example.lazier.dto.user.SignUpRequestDto;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.service.user.JwtService;
import com.example.lazier.dto.user.TokenResponseDto;
import com.example.lazier.dto.user.LoginRequestDto;
import com.example.lazier.service.user.MemberService;
import com.example.lazier.service.user.CreateTokenService;
import com.example.lazier.service.user.OAuthService;
import com.example.lazier.service.user.RedisService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/user", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class MemberController {

	public static final String AUTHORIZATION = "Authorization";
	private final CreateTokenService createTokenService;
	private final MemberService memberService;
	private final JwtService jwtService;
	private final RedisService redisService;
	private final OAuthService oAuthService;


	@ApiOperation(value = "회원가입", notes = "회원가입하기")
	@ApiResponses({
		@ApiResponse(code = 200, message = "회원가입 성공"),
		@ApiResponse(code = 400, message = "사용자 정보가 없거나 이미 가입된 이메일인 경우")
	})
	@PostMapping("/signup")
	public ResponseEntity<?> join(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
		return new ResponseEntity<>(memberService.signUp(signUpRequestDto), HttpStatus.OK);
	}


	@ApiOperation(value = "모듈 저장" , notes = "보고 싶은 모듈 저장하기")
	@ApiResponse(code = 200, message = "모듈 저장 완료")
	@PostMapping("/saveModule")
	public ResponseEntity<?> saveModule(
		@RequestBody @Valid SaveModuleRequestDto saveModuleRequestDto) {
		return new ResponseEntity<>(memberService.saveModule(saveModuleRequestDto),
			HttpStatus.OK);
	}


	@ApiOperation(value = "이메일 인증", notes = "이메일 인증하기")
	@ApiResponses({
		@ApiResponse(code = 200, message = "이메일 인증 성공"),
		@ApiResponse(code = 400, message = "회원가입을 아직 안한 경우")
	})
	@GetMapping("/email-auth")
	public ResponseEntity<?> emailAuth(@RequestParam(value = "uuid") String uuid) {
		memberService.emailAuth(uuid);
		return ResponseEntity.ok("이메일 인증 완료");
	}


	@ApiOperation(value = "로그인", notes = "로그인하기")
	@ApiResponses({
		@ApiResponse(code = 200, message = "로그인 성공"),
		@ApiResponse(code = 400, message = "아이디가 없거나 비밀번호가 잘못되었을 경우")
	})
	@PostMapping("/login")
	public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto userLogin) {
		TokenResponseDto tokenDto = createTokenService.createAccessToken(userLogin);
		redisService.setValues(tokenDto.getRefreshToken());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(AUTHORIZATION, "Bearer " + tokenDto.getAccessToken());
		return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
	}


	@ApiOperation(value = "구글 로그인", notes = "소셜 로그인하기")
	@ApiResponses({
		@ApiResponse(code = 200, message = "토큰 발급"),
		@ApiResponse(code = 400, message = "서버 측에서 잘못된 접근을 한 경우")
	})
	@PostMapping("/login/oauth2/code/google")
	public ResponseEntity<?> loginGoogle(@RequestParam("code") String code) {
		log.info("memberController-code:" + code);
		LazierUser lazierUser = oAuthService.getUser(code);
		return ResponseEntity.ok(oAuthService.loginResult(lazierUser));
	}



	@ApiOperation(value = "로그아웃", notes = "로그아웃하기")
	@ApiResponse(code = 200, message = "로그아웃 완료")
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		redisService.delValues(request);
		return ResponseEntity.ok("로그아웃 완료");
	}


	@ApiOperation(value = "테스트용")
	@ApiResponses({
		@ApiResponse(code = 401, message = "토큰이 만료되었거나 헤더에 토큰 값이 없을 경우"),
		@ApiResponse(code = 403, message = "유효하지 않는 유저인 경우")
	})
	@GetMapping("/test")
	public String test(HttpServletRequest request) {
		return request.getAttribute("userId").toString();
	}


	@ApiOperation(value = "토큰 재발급", notes = "토큰 재발급하기")
	@ApiResponses({
		@ApiResponse(code = 200, message = "토큰 재발급"),
		@ApiResponse(code = 400, message = "일치하지 않는 토큰이거나 이미 재발급된 경우")
	})
	@PostMapping("/reissue")
	public ResponseEntity<?> validateRefreshToken(
		HttpServletRequest request) {
		return new ResponseEntity<>(jwtService.validateRefreshToken(request), HttpStatus.OK);
	}

}
