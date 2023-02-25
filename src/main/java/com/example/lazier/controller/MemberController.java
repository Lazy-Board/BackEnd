package com.example.lazier.controller;

import com.example.lazier.dto.user.FindPasswordRequestDto;
import com.example.lazier.dto.user.SignUpRequestDto;
import com.example.lazier.dto.user.UpdatePasswordRequestDto;
import com.example.lazier.dto.user.MemberInfoDto;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.service.user.JwtService;
import com.example.lazier.dto.user.TokenResponseDto;
import com.example.lazier.dto.user.LoginRequestDto;
import com.example.lazier.service.user.JoinService;
import com.example.lazier.service.user.CreateTokenService;
import com.example.lazier.service.user.MemberService;
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
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

	public static final String AUTHORIZATION = "Authorization";
	private final CreateTokenService createTokenService;
	private final JoinService joinService;
	private final JwtService jwtService;
	private final RedisService redisService;
	private final OAuthService oAuthService;
	private final MemberService memberService;


	@ApiOperation(value = "회원가입")
	@ApiResponses({
		@ApiResponse(code = 200, message = "회원가입 성공"),
		@ApiResponse(code = 400, message = "사용자 정보가 없거나 이미 가입된 이메일인 경우")
	})
	@PostMapping("/signup")
	public ResponseEntity<?> join(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
		return new ResponseEntity<>(joinService.signUp(signUpRequestDto), HttpStatus.OK);
	}


	@ApiOperation(value = "이메일 인증")
	@ApiResponses({
		@ApiResponse(code = 200, message = "이메일 인증 성공"),
		@ApiResponse(code = 400, message = "회원가입을 아직 안한 경우")
	})
	@GetMapping("/email-auth")
	public ResponseEntity<?> emailAuth(@RequestParam(value = "uuid") String uuid) {
		joinService.emailAuth(uuid);
		return ResponseEntity.ok("이메일 인증 완료");
	}


	@ApiOperation(value = "로그인")
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


	@ApiOperation(value = "구글 로그인")
	@ApiResponses({
		@ApiResponse(code = 200, message = "토큰 발급"),
		@ApiResponse(code = 400, message = "서버 측에서 잘못된 접근을 한 경우")
	})
	@PostMapping("/login/oauth2/code/{provider}")
	public ResponseEntity<?> loginGoogle(@PathVariable String provider, @RequestParam String code) {
		LazierUser lazierUser = oAuthService.getUser(provider, code); //테스트 후 수정
		return ResponseEntity.ok(oAuthService.loginResult(lazierUser));
	}


	@ApiOperation(value = "로그아웃")
	@ApiResponse(code = 200, message = "로그아웃 완료")
	@PostMapping("/logout")
	public ResponseEntity logout(HttpServletRequest request) {
		redisService.delValues(request);
		return ResponseEntity.ok().build();
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


	@ApiOperation(value = "토큰 재발급")
	@ApiResponses({
		@ApiResponse(code = 200, message = "토큰 재발급"),
		@ApiResponse(code = 400, message = "일치하지 않는 토큰이거나 이미 재발급된 경우")
	})
	@PostMapping("/reissue")
	public ResponseEntity<?> validateRefreshToken(
		HttpServletRequest request) {
		return new ResponseEntity<>(jwtService.validateRefreshToken(request), HttpStatus.OK);
	}


	@ApiOperation(value = "마이 페이지 - 유저 정보 불러오기", notes = "socialType이 google인 경우 이메일 input 비활성화")
	@ApiResponse(code = 200, message = "유저 정보 불러오기 완료")
	@GetMapping("/search")
	public ResponseEntity<?> search(HttpServletRequest request) {
		return new ResponseEntity<>(memberService.showUserInfo(request), HttpStatus.OK);
	}


	@ApiOperation(value = "마이 페이지 - 유저 정보 업데이트")
	@ApiResponse(code = 200, message = "유저 정보 수정 완료")
	@PutMapping("/update")
	public ResponseEntity<?> updateUserInfo(HttpServletRequest request,
		@RequestBody @Valid MemberInfoDto memberInfoDto) {

		memberService.updateUserInfo(request, memberInfoDto);
		return ResponseEntity.ok().build();
	}


	@ApiOperation(value = "마이 페이지 - 비밀번호 변경", notes = "기존 비밀번호와 새 비밀번호 입력")
	@ApiResponses({
		@ApiResponse(code = 200, message = "비밀번호 수정 완료"),
		@ApiResponse(code = 400, message = "기존 비밀번호가 일치하지 않는 경우")
	})
	@PutMapping("/updatePassword")
	public ResponseEntity<?> updatePassword(HttpServletRequest request,
		@RequestBody @Valid UpdatePasswordRequestDto passwordDto) {

		memberService.updatePassword(request, passwordDto);
		return ResponseEntity.ok().build();
	}


	@ApiOperation(value = "마이 페이지 - 비밀번호 변경", notes = "사용자의 이메일과 전화번호 입력값이 일치한 경우 해당 이메일로 새 비밀번호 발급")
	@ApiResponses({
		@ApiResponse(code = 200, message = "이메일로 임시 비밀번호 발급 완료"),
		@ApiResponse(code = 400, message = "기존 비밀번호가 일치하지 않는 경우")
	})
	@PostMapping("/find/password")
	public ResponseEntity<?> findPassword(HttpServletRequest request,
		FindPasswordRequestDto passwordDto) {

		memberService.findPassword(request, passwordDto);
		return ResponseEntity.ok().build(); //임시 비밀번호가 000.000 로 발급되었습니다.
	}


	@ApiOperation(value = "마이 페이지 - 탈퇴")
	@ApiResponse(code = 200, message = "탈퇴 완료")
	@PostMapping("/withdrawal")
	public ResponseEntity<?> withdrawal(HttpServletRequest request) {
		memberService.withdrawal(request);
		return ResponseEntity.ok().build();
	}

}
