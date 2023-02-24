package com.example.lazier.service.module;

import com.example.lazier.dto.module.WeatherDto;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.UserWeather;
import com.example.lazier.persist.entity.module.Weather;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.persist.repository.WeatherRepository;
import com.example.lazier.scraper.NaverWeatherScraper;
import com.example.lazier.service.user.MemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherService {

    private final NaverWeatherScraper naverWeatherScraper;
    private final WeatherRepository weatherRepository;
    private final MemberService memberService;

    public void add(UserWeather userWeather) {
        LazierUser lazierUser = memberService.searchMember(userWeather.getLazierUser().getUserId());
        WeatherDto weatherDto = naverWeatherScraper.scrap(userWeather);
        weatherRepository.save(new Weather(lazierUser, weatherDto));
    }

    public WeatherDto getWeather(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        Weather weather = weatherRepository.findFirstByLazierUserOrderByUpdatedAt(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));
        return WeatherDto.of(weather);
    }
}