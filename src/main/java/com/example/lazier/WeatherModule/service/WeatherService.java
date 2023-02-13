package com.example.lazier.WeatherModule.service;

import com.example.lazier.WeatherModule.dto.WeatherDto;
import com.example.lazier.WeatherModule.persist.entity.UserWeather;

public interface WeatherService {

    void add(UserWeather userWeather);

    WeatherDto getWeather(String userId);
}
