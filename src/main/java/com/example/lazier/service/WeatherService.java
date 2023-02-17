package com.example.lazier.service;

import com.example.lazier.dto.module.WeatherDto;
import com.example.lazier.persist.entity.module.UserWeather;
import javax.servlet.http.HttpServletRequest;

public interface WeatherService {

    void add(UserWeather userWeather);

    WeatherDto getWeather(HttpServletRequest request);
}
