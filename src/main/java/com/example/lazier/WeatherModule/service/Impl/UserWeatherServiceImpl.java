package com.example.lazier.WeatherModule.service.Impl;

import com.example.lazier.WeatherModule.model.UserWeatherInput;
import com.example.lazier.WeatherModule.persist.entity.UserWeather;
import com.example.lazier.WeatherModule.persist.repository.UserWeatherRepository;
import com.example.lazier.WeatherModule.service.UserWeatherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserWeatherServiceImpl implements UserWeatherService {

    private final UserWeatherRepository userWeatherRepository;

    @Override
    @Transactional
    public void add(UserWeatherInput parameter) {
        // 중복 아이디 예외
        if (userWeatherRepository.existsById(parameter.getUserId())) {
            // custom exception handler로 예외 처리 할 예정입니다 :)
            throw new RuntimeException("사용자 정보가 이미 존재합니다.");
        }

        UserWeather userWeather = UserWeather.builder()
            .userId(parameter.getUserId())
            .cityName(parameter.getCityName())
            .locationName(parameter.getLocationName())
            .build();

        userWeatherRepository.save(userWeather);
    }
}
