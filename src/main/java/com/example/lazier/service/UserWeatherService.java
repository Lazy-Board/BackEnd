package com.example.lazier.service;

import com.example.lazier.dto.module.UserWeatherDto;
import com.example.lazier.dto.module.UserWeatherInput;
import javax.servlet.http.HttpServletRequest;

public interface UserWeatherService {

    void add(HttpServletRequest request, UserWeatherInput parameter);

    UserWeatherDto detail(HttpServletRequest request);

    void update(HttpServletRequest request, UserWeatherInput parameter);

    void delete(HttpServletRequest request);
}
