package com.example.lazier.WeatherModule.service.Impl;

import com.example.lazier.WeatherModule.dto.WeatherDto;
import com.example.lazier.WeatherModule.exception.UserNotFoundException;
import com.example.lazier.WeatherModule.persist.entity.UserWeather;
import com.example.lazier.WeatherModule.persist.entity.Weather;
import com.example.lazier.WeatherModule.persist.repository.WeatherRepository;
import com.example.lazier.WeatherModule.scraper.Scraper;
import com.example.lazier.WeatherModule.service.WeatherService;
import javax.servlet.http.HttpServletRequest;
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
    public WeatherDto getWeather(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        Weather weather = weatherRepository.findFirstByUserIdOrderByUpdatedAt(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));
        return WeatherDto.of(weather);
    }
}