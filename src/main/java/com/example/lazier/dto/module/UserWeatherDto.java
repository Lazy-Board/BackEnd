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
    long locationId;

    public static UserWeatherDto of(UserWeather userWeather) {
        return UserWeatherDto.builder()
            .userId(userWeather.getLazierUser().getUserId())
            .locationId(userWeather.getWeatherLocation().getLocationId())
            .build();
    }
}
