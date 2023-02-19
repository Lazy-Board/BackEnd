package com.example.lazier.stockModule.controller;

import com.example.lazier.stockModule.dto.UserStockInput;
import com.example.lazier.stockModule.persist.repository.UserStockRepository;
import com.example.lazier.stockModule.service.UserStockService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
@RequestMapping("/stock")
public class StockController {

    private final UserStockService userStockService;

    private final UserStockRepository userStockRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addStockInfo(HttpServletRequest request,
        @RequestBody @Valid UserStockInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);

        if (userStockRepository.existsById(parameter.getUserId())) {
            return new ResponseEntity<>(
            userStockService.getUserWantedStock(parameter.getUserId()), HttpStatus.OK);
        } else {
            userStockService.add(parameter);
            return ResponseEntity.status(HttpStatus.CREATED).body("저장되었습니다.");
        }
    }

    // Main 페이지 - 주식 정보 VIEW (4가지)
    @GetMapping("/search")
    public ResponseEntity<?> getUserPartialStock(HttpServletRequest request,
        @RequestBody @Valid UserStockInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);
        return new ResponseEntity<>(
            userStockService.getUserPartialStock(userId), HttpStatus.OK);
    }

    // 상세 페이지 - 주식 정보 전체 VIEW
    @GetMapping("/detail")
    public ResponseEntity<?> getUserAllStock(HttpServletRequest request,
        @RequestBody @Valid UserStockInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);
        return new ResponseEntity<>(
            userStockService.getUserWantedStock(userId), HttpStatus.OK);
    }

    // 주식 종목 선택 정보 업데이트
    @PutMapping("/update")
    public ResponseEntity<?> updateUserStock(HttpServletRequest request,
        @RequestBody @Valid UserStockInput parameter) {
        userStockService.update(request, parameter);

        return ResponseEntity.ok().body("업데이트 되었습니다.");
    }

}
