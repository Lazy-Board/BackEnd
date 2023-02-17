package com.example.lazier.WeatherModule.scraper;

import com.example.lazier.WeatherModule.dto.WeatherDto;
import com.example.lazier.WeatherModule.persist.entity.UserWeather;

public interface Scraper {

    WeatherDto scrap(UserWeather userWeather);
}
