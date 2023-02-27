package com.example.lazier.controller;

import com.example.lazier.dto.module.UserExchangeInput;
import com.example.lazier.service.module.UserExchangeService;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/exchange", produces = "application/json; charset=utf8")
public class ExchangeController {

  private final UserExchangeService userExchangeService;

  @ApiOperation(value = "환율 정보 저장")
  @PostMapping("/add")
  public ResponseEntity<?> addExchangeInfo(HttpServletRequest request) {
    userExchangeService.add(request);
    return ResponseEntity.status(HttpStatus.CREATED).body("저장되었습니다.");
  }


  // Main Page - 환율 정보 VIEW
  @ApiOperation(value = "환율 정보 일부만 조회(통화명, 국가명, 전일대비, 등락율, 매매기준율)")
  @GetMapping("/search")
  public ResponseEntity<?> getUserPartialExchange(HttpServletRequest request) {
    return new ResponseEntity<>(
        userExchangeService.getPartialExchange(request), HttpStatus.OK);
  }

  // 상세 페이지 - 환율 정보 VIEW
  @ApiOperation(value = "환율 정보 상세 조회")
  @GetMapping("/detail")
  public ResponseEntity<?> getUserAllExchange(HttpServletRequest request) {
    return new ResponseEntity<>(
        userExchangeService.getExchange(request), HttpStatus.OK);
  }

  // 환율 선택 정보 업데이트
  @ApiOperation(value = "사용자 환율 선택값 업데이트")
  @PutMapping("/update")
  public ResponseEntity<?> updateUserExchange(HttpServletRequest request,
      @RequestBody UserExchangeInput parameter) {
    userExchangeService.update(request, parameter);
    return ResponseEntity.ok().body("업데이트 되었습니다.");
  }

}
