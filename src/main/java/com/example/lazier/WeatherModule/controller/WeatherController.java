package com.example.lazier.WeatherModule.controller;

import com.example.lazier.WeatherModule.service.WeatherService;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<?> getWeather(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return new ResponseEntity<>(weatherService.getWeather(userId), HttpStatus.OK);
    }
}
