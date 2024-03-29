package com.example.lazier.scraper;

import com.example.lazier.dto.module.WeatherDto;
import com.example.lazier.exception.AddressNotFoundException;
import com.example.lazier.persist.entity.module.WeatherLocation;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NaverWeatherScraper {

    private static final String STATISTICS_URL = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%s%s%s";

    public WeatherDto scrap(WeatherLocation weatherLocation) {

        try {
            String url = String.format(STATISTICS_URL, weatherLocation.getCityName(),
                weatherLocation.getLocationName(), "날씨");

            Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
                .header("scheme", "https")
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("accept-encoding", "gzip, deflate, br")
                .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6")
                .header("cache-control", "no-cache")
                .header("pragma", "no-cache")
                .header("upgrade-insecure-requests", "1")
                .get();

            String currentStatus = document.getElementsByClass("temperature_text").get(0)
                .text(); // 현재 온도8.6°
            String todayTemperature = currentStatus.substring(5); // 8.6°

            String summary = document.getElementsByClass("summary").get(0)
                .text(); // 어제보다 2.2° 높아요 구름많음
            String[] summaries = summary.split(" ");
            String comparison = summaries[1] + " " + summaries[2]; // 2.2° 높아요
            String weatherDetail = summaries[3]; // 구름많음

            String detail = document.getElementsByClass("summary_list").get(0)
                .text(); // 체감 7.5° 습도 51% 바람(서풍) 2.2m/s
            String[] details = detail.split(" ");
            String effective = details[1]; // 6.7°
            String humidity = details[3]; // 55%
            String windDirection = details[4].substring(3, details[4].length() - 1); // 서풍
            String windSpeed = details[5]; // 2.5m/s

            String air = document.getElementsByClass("today_chart_list").get(0)
                .text(); // 미세먼지 보통 초미세먼지 나쁨 자외선 좋음 일몰 18:01
            String[] airs = air.split(" ");
            String fine = airs[1]; // 보통
            String ultrafine = airs[3]; // 보통
            String uv = airs[5]; // 좋음

            String temperatureDetail = document.getElementsByClass("temperature_inner").get(0)
                .text(); // 최저기온-1° / 최고기온9°
            String[] temperatures = temperatureDetail.split(" / ");
            String low = temperatures[0].substring(4); // -1°
            String high = temperatures[1].substring(4); // 9°

            Thread.sleep(1000L);
            return WeatherDto.builder()
                .cityName(weatherLocation.getCityName())
                .locationName(weatherLocation.getLocationName())
                .temperature(todayTemperature)
                .weatherComparison(comparison)
                .effectiveTemperature(effective)
                .weatherInformation(weatherDetail)
                .humidity(humidity)
                .windDirection(windDirection)
                .windSpeed(windSpeed)
                .fineParticle(fine)
                .ultrafineParticle(ultrafine)
                .ultraviolet(uv)
                .highestTemperature(high)
                .lowestTemperature(low)
                .updatedAt(updatedDateText(LocalDateTime.now()))
                .build();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException e) {
            throw new AddressNotFoundException("잘못된 주소 형태 입니다.");
        }
    }

    public String updatedDateText(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return now != null ? now.format(formatter) : "";
    }
}
