package com.example.lazier.WeatherModule.controller;

import com.example.lazier.WeatherModule.model.UserWeatherInput;
import com.example.lazier.WeatherModule.service.UserWeatherService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        userWeatherService.add(request, parameter);

        return ResponseEntity.status(HttpStatus.CREATED).body("저장되었습니다.");
    }

    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        return new ResponseEntity<>(userWeatherService.detail(request), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUserInfo(HttpServletRequest request,
        @RequestBody @Valid UserWeatherInput parameter) {
        userWeatherService.update(request, parameter);

        return ResponseEntity.ok().body("업데이트 되었습니다.");
    }

    @DeleteMapping("/user-info")
    public ResponseEntity<?> deleteUserInfo(HttpServletRequest request) {
        userWeatherService.delete(request);

        return ResponseEntity.ok().body("삭제되었습니다.");
    }
}