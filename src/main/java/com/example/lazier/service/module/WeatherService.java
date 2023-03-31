package com.example.lazier.service.module;

import com.example.lazier.dto.module.WeatherDto;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.persist.entity.module.UserWeather;
import com.example.lazier.persist.entity.module.Weather;
import com.example.lazier.persist.entity.module.WeatherLocation;
import com.example.lazier.persist.repository.UserWeatherRepository;
import com.example.lazier.persist.repository.WeatherRepository;
import com.example.lazier.scraper.NaverWeatherScraper;
import com.example.lazier.service.user.MyPageService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherService {

    private final NaverWeatherScraper naverWeatherScraper;
    private final WeatherRepository weatherRepository;
    private final UserWeatherRepository userWeatherRepository;
    private final MyPageService myPageService;

    public void add(WeatherLocation weatherLocation) {
        WeatherDto weatherDto = naverWeatherScraper.scrap(weatherLocation);
        weatherRepository.save(new Weather(weatherLocation, weatherDto));
    }

    public WeatherDto getWeather(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = myPageService.searchMember(userId);

        UserWeather userWeather = userWeatherRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        Optional<Weather> optionalWeather = weatherRepository.findTopByWeatherLocationOrderByUpdatedAtDesc(
            userWeather.getWeatherLocation());

        return optionalWeather.map(WeatherDto::of).orElse(null);
    }
}
