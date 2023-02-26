package com.example.lazier.controller;

import com.example.lazier.dto.user.FindPasswordRequestDto;
import com.example.lazier.dto.user.MemberInfoDto;
import com.example.lazier.dto.user.MemberModuleUpdateRequestDto;
import com.example.lazier.dto.user.UpdatePasswordRequestDto;
import com.example.lazier.service.user.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MyPageController {

	private final MemberService memberService;

	@ApiOperation(value = "마이 페이지 - 유저 정보 불러오기", notes = "socialType이 google인 경우 이메일 input 비활성화")
	@ApiResponse(code = 200, message = "유저 정보 불러오기 완료")
	@GetMapping("/search")
	public ResponseEntity<?> search(HttpServletRequest request) {
		return new ResponseEntity<>(memberService.showUserInfo(request), HttpStatus.OK);
	}


	@ApiOperation(value = "마이 페이지 - 유저 정보 업데이트", notes = "유저 정보 수정하기")
	@ApiResponse(code = 200, message = "유저 정보 수정 완료")
	@PutMapping("/update")
	public ResponseEntity<?> updateUserInfo(HttpServletRequest request,
		@RequestBody @Valid MemberInfoDto memberInfoDto) {

		memberService.updateUserInfo(request, memberInfoDto);
		return ResponseEntity.ok("유저 정보 업데이트 완료");
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
		return ResponseEntity.ok("비밀번호 수정 완료");
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
		return ResponseEntity.ok("임시 비밀번호 발급 완료"); //임시 비밀번호가 000.000 로 발급되었습니다.
	}


	@ApiOperation(value = "마이 페이지 - 탈퇴", notes = "유저 탈퇴")
	@ApiResponse(code = 200, message = "탈퇴 완료")
	@PostMapping("/withdrawal")
	public ResponseEntity<?> withdrawal(HttpServletRequest request) {
		memberService.withdrawal(request);
		return ResponseEntity.ok("탈퇴 완료");
	}


	@ApiOperation(value = "마이 페이지 - 모듈 업데이트", notes = "보고 싶은 모듈 바꾸기")
	@ApiResponse(code = 200, message = "모듈 업데이트 완료")
	@PostMapping("/updateModule")
	public ResponseEntity<?> updateModule(HttpServletRequest request,
		@RequestBody @Valid MemberModuleUpdateRequestDto memberModuleUpdateRequestDto) {
		memberService.updateModule(request, memberModuleUpdateRequestDto);
		return ResponseEntity.ok("모듈 업데이트 완료");
	}

	//사진

}
