package com.example.lazier.WeatherModule.service.Impl;

import com.example.lazier.WeatherModule.dto.UserWeatherDto;
import com.example.lazier.WeatherModule.exception.UserAlreadyExistException;
import com.example.lazier.WeatherModule.exception.UserNotFoundException;
import com.example.lazier.WeatherModule.model.UserWeatherInput;
import com.example.lazier.WeatherModule.persist.entity.UserWeather;
import com.example.lazier.WeatherModule.persist.repository.UserWeatherRepository;
import com.example.lazier.WeatherModule.service.UserWeatherService;
import com.example.lazier.WeatherModule.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserWeatherServiceImpl implements UserWeatherService {

    private final UserWeatherRepository userWeatherRepository;
    private final WeatherService weatherService;

    @Override
    @Transactional
    public void add(UserWeatherInput parameter) {
        // 중복 아이디 예외
        if (userWeatherRepository.existsById(parameter.getUserId())) {
            throw new UserAlreadyExistException("사용자 정보가 이미 존재합니다.");
        }

        UserWeather userWeather = UserWeather.builder()
            .userId(parameter.getUserId())
            .cityName(parameter.getCityName())
            .locationName(parameter.getLocationName())
            .build();

        userWeatherRepository.save(userWeather);
        // 새로 저장된 위치 날씨 정보 저장
        weatherService.add(userWeather);
    }

    @Override
    @Transactional(readOnly = true)
    public UserWeatherDto detail(String userId) {
        UserWeather userWeather = userWeatherRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));
        return UserWeatherDto.of(userWeather);
    }

    @Override
    @Transactional
    public void update(UserWeatherInput parameter) {
        UserWeather userWeather = userWeatherRepository.findById(parameter.getUserId())
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userWeather.updateUser(parameter.getCityName(), parameter.getLocationName());
        // 업데이트 된 날씨 정보 저장
        weatherService.add(userWeather);
    }

    @Override
    @Transactional
    public void delete(String userId) {
        UserWeather userWeather = userWeatherRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userWeatherRepository.delete(userWeather);
    }
}
