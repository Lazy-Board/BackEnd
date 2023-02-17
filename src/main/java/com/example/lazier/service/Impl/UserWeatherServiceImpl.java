package com.example.lazier.service.Impl;

import com.example.lazier.dto.module.UserWeatherDto;
import com.example.lazier.exception.UserAlreadyExistException;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.dto.module.UserWeatherInput;
import com.example.lazier.persist.entity.module.UserWeather;
import com.example.lazier.persist.repository.UserWeatherRepository;
import com.example.lazier.service.UserWeatherService;
import com.example.lazier.service.WeatherService;
import javax.servlet.http.HttpServletRequest;
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
    public void add(HttpServletRequest request, UserWeatherInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);

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
    public UserWeatherDto detail(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        UserWeather userWeather = userWeatherRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));
        return UserWeatherDto.of(userWeather);
    }

    @Override
    @Transactional
    public void update(HttpServletRequest request, UserWeatherInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);

        UserWeather userWeather = userWeatherRepository.findById(parameter.getUserId())
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userWeather.updateUser(parameter.getCityName(), parameter.getLocationName());
        // 업데이트 된 날씨 정보 저장
        weatherService.add(userWeather);
    }

    @Override
    @Transactional
    public void delete(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        UserWeather userWeather = userWeatherRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userWeatherRepository.delete(userWeather);
    }
}
