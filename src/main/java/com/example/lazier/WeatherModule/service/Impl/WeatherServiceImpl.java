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

    @Override
    @Transactional(readOnly = true)
    public WeatherDto getWeather(String userId) {
        // custom exception handler로 예외 처리 할 예정입니다 :)
        Weather weather = weatherRepository.findFirstByUserIdOrderByUpdatedAt(userId)
            .orElseThrow(() -> new RuntimeException("사용자 정보를 조회 할 수 없습니다."));
        return WeatherDto.of(weather);
    }
}
