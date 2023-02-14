package com.example.lazier.WeatherModule.service;

import com.example.lazier.WeatherModule.dto.UserWeatherDto;
import com.example.lazier.WeatherModule.model.UserWeatherInput;
import javax.servlet.http.HttpServletRequest;

public interface UserWeatherService {

    void add(HttpServletRequest request, UserWeatherInput parameter);

    UserWeatherDto detail(HttpServletRequest request);

    void update(HttpServletRequest request, UserWeatherInput parameter);

    void delete(HttpServletRequest request);
}
