package com.example.lazier.controller;

import com.example.lazier.dto.module.UserQuotesInput;
import com.example.lazier.service.module.UserQuotesService;
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

@Api(tags = {"사용자 설정 문구를 위한 api"})
@RestController
@AllArgsConstructor
@RequestMapping("/userQuotes")
public class UserQuotesController {

    private final UserQuotesService userQuotesService;

    @ApiOperation(value = "문구를 저장하기 위한 메소드")
    @PostMapping
    public ResponseEntity<?> add(HttpServletRequest request,
        @RequestBody @Valid UserQuotesInput parameter) {

        userQuotesService.add(request, parameter);
        return ResponseEntity.status(HttpStatus.CREATED).body("저장되었습니다.");
    }

    @ApiOperation(value = "문구를 조회하기 위한 메소드")
    @GetMapping
    public ResponseEntity<?> get(HttpServletRequest request) {

        return new ResponseEntity<>(userQuotesService.get(request), HttpStatus.OK);
    }


    @ApiOperation(value = "문구를 수정(업데이트)하기위한 메소드")
    @PutMapping
    public ResponseEntity<?> update(HttpServletRequest request,
        @RequestBody @Valid UserQuotesInput parameter) {

        userQuotesService.update(request, parameter);

        return ResponseEntity.ok().body("업데이트 되었습니다.");
    }

    @ApiOperation(value = "문구를 삭제하기 위한 메소드")
    @DeleteMapping
    public ResponseEntity<?> delete(HttpServletRequest request) {

        userQuotesService.delete(request);
        return ResponseEntity.ok().body("삭제되었습니다.");
    }
}
