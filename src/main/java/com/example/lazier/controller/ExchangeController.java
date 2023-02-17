package com.example.lazier.controller;

import com.example.lazier.dto.module.UserExchangeInput;
import com.example.lazier.persist.repository.UserExchangeRepository;
import com.example.lazier.service.UserExchangeService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange")
public class ExchangeController {

    private final UserExchangeService userExchangeService;
    private final UserExchangeRepository userExchangeRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addExchangeInfo(HttpServletRequest request,
        @Valid UserExchangeInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);

        if (userExchangeRepository.existsById(parameter.getUserId())) {
            return new ResponseEntity<>(
            userExchangeService.getUserWantedExchange(parameter.getUserId()), HttpStatus.OK);
        } else {
            userExchangeService.add(parameter);
            return ResponseEntity.status(HttpStatus.CREATED).body("저장되었습니다.");
        }
    }

    // Main Page - 환율 정보 VIEW
    @GetMapping("/search")
    public ResponseEntity<?> getUserPartialExchange(HttpServletRequest request,
        @Valid UserExchangeInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);
        return new ResponseEntity<>(
            userExchangeService.getUserPartialExchange(parameter.getUserId()), HttpStatus.OK);
    }

    // 상세 페이지 - 환율 정보 VIEW
    @GetMapping("/detail")
    public ResponseEntity<?> getUserAllExchange(HttpServletRequest request,
        @Valid UserExchangeInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);
        return new ResponseEntity<>(
            userExchangeService.getUserWantedExchange(parameter.getUserId()), HttpStatus.OK);
    }

    // 환율 선택 정보 업데이트
    @PutMapping("/update")
    public ResponseEntity<?> updateUserExchange(HttpServletRequest request,
        @Valid UserExchangeInput parameter) {
        userExchangeService.update(request, parameter);

        return ResponseEntity.ok().body("업데이트 되었습니다.");
    }

}
