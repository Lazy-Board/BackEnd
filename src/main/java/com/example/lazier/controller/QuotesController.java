package com.example.lazier.controller;

import com.example.lazier.service.module.QuotesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"명언 정보를 제공하는 api"})
@RestController
@AllArgsConstructor
@RequestMapping("/quotes")
public class QuotesController {

    public final QuotesService quotesService;

    @ApiOperation(value = "랜덤한 명언을 반환하는 메소드")
    @GetMapping
    public ResponseEntity<?> getQuotes() {
        return new ResponseEntity<>(quotesService.get(), HttpStatus.OK);
    }
}
