package com.example.lazier.controller;

import com.example.lazier.service.module.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"날씨를 조회하기 위한 api"})
@RestController
@AllArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;
    @ApiOperation(value = "사용자가 지정한 지역의 날씨 정보를 조회 할 수 있는 api 입니다.")
    @GetMapping
    public ResponseEntity<?> getWeather(HttpServletRequest request) {
        return new ResponseEntity<>(weatherService.getWeather(request), HttpStatus.OK);
    }
}
