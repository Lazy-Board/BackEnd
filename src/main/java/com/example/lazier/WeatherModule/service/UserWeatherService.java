package com.example.lazier.WeatherModule.service;

import com.example.lazier.WeatherModule.dto.UserWeatherDto;
import com.example.lazier.WeatherModule.model.UserWeatherInput;

public interface UserWeatherService {

    void add(UserWeatherInput parameter);

    UserWeatherDto detail(String userId);
}
