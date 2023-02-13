package com.example.lazier.WeatherModule.service.Impl;

import com.example.lazier.WeatherModule.dto.WeatherDto;
import com.example.lazier.WeatherModule.persist.entity.UserWeather;
import com.example.lazier.WeatherModule.persist.entity.Weather;
import com.example.lazier.WeatherModule.persist.repository.WeatherRepository;
import com.example.lazier.WeatherModule.scraper.Scraper;
import com.example.lazier.WeatherModule.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final Scraper naverWeatherScraper;
    private final WeatherRepository weatherRepository;

    @Override
    @Transactional
    public void add(UserWeather userWeather) {
        WeatherDto weatherDto = naverWeatherScraper.scrap(userWeather);
        weatherRepository.save(new Weather(weatherDto));
    }
}
