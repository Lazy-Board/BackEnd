package com.example.lazier.scheduler;

import com.example.lazier.persist.entity.module.UserWeather;
import com.example.lazier.persist.entity.module.Weather;
import com.example.lazier.persist.repository.UserWeatherRepository;
import com.example.lazier.persist.repository.WeatherRepository;
import com.example.lazier.service.module.WeatherService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class WeatherScraperScheduler {

    private final UserWeatherRepository userWeatherRepository;
    private final WeatherRepository weatherRepository;
    private final WeatherService weatherService;

    @Scheduled(cron = "${scheduler.scrap.weather}")
    public void naverWeatherScheduling() {
        // 2일 이전 데이터 삭제
        List<Weather> oldData = weatherRepository.findAllByUpdatedAtBefore(
            dateText(LocalDateTime.now().minusDays(2)));
        weatherRepository.deleteAll(oldData);

        log.info("weather scraping is started");
        List<UserWeather> userWeathers = userWeatherRepository.findAll();

        for (UserWeather userWeather : userWeathers) {
            log.info(userWeather.getLazierUser().getUserId().toString());
            weatherService.add(userWeather);
            // 연속 요청 방지
            try {
                Thread.sleep(3000); // 3초 정지
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    public String dateText(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return now != null ? now.format(formatter) : "";
    }
}
