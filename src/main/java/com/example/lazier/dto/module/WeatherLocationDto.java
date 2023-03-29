package com.example.lazier.dto.module;


import com.example.lazier.persist.entity.module.WeatherLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherLocationDto {

    String cityName;
    String locationName;

    public static WeatherLocationDto of(WeatherLocation weatherLocation) {
        return WeatherLocationDto.builder()
            .cityName(weatherLocation.getCityName())
            .locationName(weatherLocation.getLocationName())
            .build();
    }
}
