package com.example.lazier.controller;

import com.example.lazier.dto.user.DeleteOneModuleRequestDto;
import com.example.lazier.dto.user.UpdateOneModuleRequestDto;
import com.example.lazier.service.user.MainService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/user", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class MainController {

	private final MainService mainService;

	@ApiOperation(value = "메인 : 모듈 정보 불러오기", notes = "사용자가 선택한 모듈 불러오기")
	@ApiResponse(code = 200, message = "모듈 불러오기 완료")
	@GetMapping("/searchModule")
	public ResponseEntity<?> searchModule(HttpServletRequest request) {
		return new ResponseEntity<>(mainService.searchModule(request), HttpStatus.OK);
	}


	@ApiOperation(value = "메인 : 모듈 삭제하기", notes = "사용자가 선택한 모듈 삭제하기")
	@ApiResponse(code = 200, message = "모듈 삭제하기 완료")
	@PostMapping("/deleteOneModule")
	public ResponseEntity<?> deleteOneModule(HttpServletRequest request,
		DeleteOneModuleRequestDto deleteOneModuleRequestDto) {
		mainService.deleteOneModule(request, deleteOneModuleRequestDto);
		return ResponseEntity.ok("모듈 삭제 완료");
	}


	@ApiOperation(value = "메인 : 모듈 추가하기", notes = "사용자가 선택한 모듈 추가하기")
	@ApiResponse(code = 200, message = "모듈 추가하기 완료")
	@PostMapping("updateOneModule")
	public ResponseEntity<?> updateOneModule(HttpServletRequest request,
		UpdateOneModuleRequestDto updateOneModuleRequestDto) {
		mainService.updateOneModule(request, updateOneModuleRequestDto);
		return ResponseEntity.ok("모듈 추가 완료");
	}


}
