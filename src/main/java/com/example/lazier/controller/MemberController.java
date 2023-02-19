package com.example.lazier.controller;

import com.example.lazier.dto.user.FindPasswordRequestDto;
import com.example.lazier.dto.user.UpdatePasswordRequestDto;
import com.example.lazier.dto.user.MemberInfo;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.service.user.JwtService;
import com.example.lazier.dto.user.TokenResponseDto;
import com.example.lazier.dto.user.LoginRequestDto;
import com.example.lazier.service.user.JoinService;
import com.example.lazier.service.user.CreateTokenService;
import com.example.lazier.service.user.MemberService;
import com.example.lazier.service.user.OAuthService;
import com.example.lazier.service.user.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

	private final CreateTokenService createTokenService;
	private final JoinService joinService;
	private final JwtService jwtService;
	private final RedisService redisService;
	private final OAuthService oAuthService;
	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<?> join(@RequestBody @Valid MemberInfo memberInfo) {
		return new ResponseEntity<>(joinService.signUp(memberInfo), HttpStatus.OK);
	}

	@GetMapping("/email-auth")
	public ResponseEntity<?> emailAuth(@RequestParam(value = "uuid") String uuid) {
		joinService.emailAuth(uuid);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto userLogin) {
		TokenResponseDto tokenDto = createTokenService.createAccessToken(userLogin);
		redisService.setValues(tokenDto.getRefreshToken());

		return ResponseEntity.ok(tokenDto);
	}

	@PostMapping("/login/oauth2/code/{provider}")
	public ResponseEntity<?> loginGoogle(@PathVariable String provider, @RequestParam String code) {
		LazierUser lazierUser = oAuthService.getUser(provider, code); //테스트 후 수정
		return ResponseEntity.ok(oAuthService.loginResult(lazierUser));
	}

	@PostMapping("/logout")
	public ResponseEntity logout(HttpServletRequest request) {
		redisService.delValues(request);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/test")
	public String test(HttpServletRequest request) { //Authentication 헤더로 "Bearer " + 토큰
		return request.getAttribute("userId").toString();
	}

	@PostMapping("/reissue")
	public ResponseEntity<?> validateRefreshToken(
		HttpServletRequest request) { //RefreshToken 헤더로 "Bearer " + 토큰
		return new ResponseEntity<>(jwtService.validateRefreshToken(request), HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<?> search(HttpServletRequest request) {
		return new ResponseEntity<>(memberService.showUserInfo(request), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUserInfo(HttpServletRequest request,
		@RequestBody @Valid MemberInfo memberInfo) {

		memberService.updateUserInfo(request, memberInfo);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/updatePassword")
	public ResponseEntity<?> updatePassword(HttpServletRequest request,
		@RequestBody @Valid UpdatePasswordRequestDto passwordDto) {

		memberService.updatePassword(request, passwordDto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/find/password")
	public ResponseEntity<?> findPassword(HttpServletRequest request,
		FindPasswordRequestDto passwordDto) {

		memberService.findPassword(request, passwordDto);
		return ResponseEntity.ok().build(); //임시 비밀번호가 000.000 로 발급되었습니다.
	}

	@PostMapping("/withdrawal")
	public ResponseEntity<?> withdrawal(HttpServletRequest request) {
		memberService.withdrawal(request);
		return ResponseEntity.ok().build();
	}

}
