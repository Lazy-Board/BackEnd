package com.example.lazier.controller;

import com.example.lazier.service.user.MainService;
import com.example.lazier.service.user.MyPageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/user", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class MainController {

	private final MainService mainService;

	@ApiOperation(value = "메인 : 모듈 정보 불러오기", notes= "사용자가 선택한 모듈 불러오기")
	@ApiResponse(code = 200, message = "모듈 불러오기 완료")
	@GetMapping("/main")
	public ResponseEntity<?> main(HttpServletRequest request) {
		return new ResponseEntity<>(mainService.search(request), HttpStatus.OK);
	}

}
