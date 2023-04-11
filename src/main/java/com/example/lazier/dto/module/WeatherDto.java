package com.example.lazier.dto.module;

import com.example.lazier.persist.entity.module.Weather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {

    long locationId;
    String cityName; // 시/군/구 이름
    String locationName; // 동 이름
    String icon; // 날씨 아이콘

    String description; // 날씨 상세정보
    String engWeather; // 영어 날씨
    String weatherId; // 날씨 아이디

    String temperature;
    String effectiveTemperature; // 체감 온도
    String highestTemperature;
    String lowestTemperature;
    String pressure;
    String humidity;
    String windSpeed;
    String windDirection;
    String updatedAt;

    public static WeatherDto of(Weather weather) {
        return WeatherDto.builder()
            .locationId(weather.getWeatherLocation().getLocationId())
            .cityName(weather.getCityName())
            .locationName(weather.getLocationName())
            .icon(weather.getIcon())
            .description(weather.getDescription())
            .engWeather(weather.getEngWeather())
            .weatherId(weather.getWeatherId())
            .temperature(weather.getTemperature())
            .effectiveTemperature(weather.getEffectiveTemperature())
            .highestTemperature(weather.getHighestTemperature())
            .lowestTemperature(weather.getLowestTemperature())
            .pressure(weather.getPressure())
            .humidity(weather.getHumidity())
            .windSpeed(weather.getWindSpeed())
            .windDirection(weather.getWindDirection())
            .updatedAt(weather.getUpdatedAt())
            .build();
    }
}
