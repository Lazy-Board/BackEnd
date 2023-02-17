package com.example.lazier.scraper;

import com.example.lazier.dto.module.WeatherDto;
import com.example.lazier.persist.entity.module.UserWeather;

public interface Scraper {

    WeatherDto scrap(UserWeather userWeather);
}
