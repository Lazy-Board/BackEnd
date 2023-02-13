package com.example.lazier.WeatherModule.service;

import com.example.lazier.WeatherModule.dto.WeatherDto;
import com.example.lazier.WeatherModule.persist.entity.UserWeather;
import javax.servlet.http.HttpServletRequest;

public interface WeatherService {

    void add(UserWeather userWeather);

    WeatherDto getWeather(HttpServletRequest request);
}
