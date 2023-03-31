package com.example.lazier.controller;

import com.example.lazier.dto.module.TrafficInput;
import com.example.lazier.service.module.TrafficService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"출근정보 저장 및 조회하는 api"})
@RestController
@RequestMapping(value = "/traffic", produces = "application/json; charset=utf8")
@AllArgsConstructor
public class TrafficController {

  private final TrafficService trafficService;

  @ApiOperation(value = "출발지와 도착지를 저장하는 메소드")
  @PostMapping
  public ResponseEntity<?> addUserInfo(HttpServletRequest request,
      @RequestBody @Valid TrafficInput parameter) {
    trafficService.add(request, parameter);
    return ResponseEntity.ok().body("저장되었습니다.");
  }

  @ApiOperation(value = "출발지와 도착지 정보를 조회하는 메소드")
  @GetMapping
  public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
    return new ResponseEntity<>(trafficService.getUserInfo(request), HttpStatus.OK);
  }

  @ApiOperation(value = "출근지와 도착지 정보를 업데이트 하는 메소드")
  @PutMapping
  public ResponseEntity<?> updateUserInfo(HttpServletRequest request,
      @RequestBody @Valid TrafficInput parameter) {
    trafficService.update(request, parameter);

    return ResponseEntity.ok().body("업데이트 되었습니다.");
  }

  @ApiOperation(value = "출근지와 도착지 정보를 삭제하는 메소드")
  @DeleteMapping
  public ResponseEntity<?> deleteUserInfo(HttpServletRequest request) {
    trafficService.delete(request);

    return ResponseEntity.ok().body("삭제되었습니다.");
  }

  @ApiOperation(value = "출근길 소요시간을 조회하는 메소드")
  @GetMapping("/duration")
  public ResponseEntity<?> getTrafficDuration(HttpServletRequest request) {
    return new ResponseEntity<>(trafficService.getTrafficDuration(request), HttpStatus.OK);
  }
}
