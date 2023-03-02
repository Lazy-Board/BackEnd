package com.example.lazier.controller;

import com.example.lazier.dto.module.UpdateStockDto;
import com.example.lazier.service.module.UserStockService;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/stock", produces = "application/json; charset=utf8")
@Slf4j
public class StockController {
    private final UserStockService userStockService;

    @ApiOperation(value = "주식 정보 저장")
    @PostMapping("/add")
    public ResponseEntity<?> addStockInfo(HttpServletRequest request) {
        userStockService.add(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("저장되었습니다.");
    }

    // Main 페이지 - 주식 정보 VIEW (4가지)
    @ApiOperation(value = "주식 정보 조회 - 주식 종목, 전일비, 현재가, 등락률")
    @GetMapping("/search")
    public ResponseEntity<?> getUserPartialStock(HttpServletRequest request) {
        return new ResponseEntity<>(
            userStockService.getPartialStock(request), HttpStatus.OK);
    }

    // 상세 페이지 - 주식 정보 전체 VIEW
    @ApiOperation(value = "주식 상세 정보 조회")
    @GetMapping("/detail")
    public ResponseEntity<?> getUserAllStock(HttpServletRequest request) {
        return new ResponseEntity<>(
            userStockService.getStock(request), HttpStatus.OK);
    }

    // 주식 종목 선택 정보 업데이트
    @ApiOperation(value = "주식 종목 조회 정보 업데이트")
    @PostMapping("/update")
    public ResponseEntity<?> updateUserStock(HttpServletRequest request,
        @RequestBody UpdateStockDto updateStockDto) {
        userStockService.update(request, updateStockDto);
        return ResponseEntity.ok().body("업데이트 되었습니다.");
    }

}
