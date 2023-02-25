package com.example.lazier.dto.module;


import com.example.lazier.persist.entity.module.UserWeather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWeatherDto {

    long userId;
    String cityName;
    String locationName;

    public static UserWeatherDto of(UserWeather userWeather) {
        return UserWeatherDto.builder()
            .userId(userWeather.getLazierUser().getUserId())
            .cityName(userWeather.getCityName())
            .locationName(userWeather.getLocationName())
            .build();
    }
}
