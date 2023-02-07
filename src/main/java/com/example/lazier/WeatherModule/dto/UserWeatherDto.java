package com.example.lazier.WeatherModule.dto;


import com.example.lazier.WeatherModule.persist.entity.UserWeather;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserWeatherDto {

    String userId;
    String cityName;
    String locationName;

    public static UserWeatherDto of (UserWeather userWeather) {
        return UserWeatherDto.builder()
            .userId(userWeather.getUserId())
            .cityName(userWeather.getCityName())
            .locationName(userWeather.getLocationName())
            .build();
    }
}
