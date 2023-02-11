package com.example.lazier.WeatherModule.controller;

import com.example.lazier.WeatherModule.model.UserWeatherInput;
import com.example.lazier.WeatherModule.service.UserWeatherService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/weather")
public class UserWeatherController {
    private final UserWeatherService userWeatherService;
    @PostMapping("/user-info")
    public ResponseEntity<?> addUserInfo(HttpServletRequest request, @RequestBody @Valid
        UserWeatherInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);

        userWeatherService.add(parameter);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
