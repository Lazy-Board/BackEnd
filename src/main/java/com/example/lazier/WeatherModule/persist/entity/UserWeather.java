package com.example.lazier.WeatherModule.persist.entity;

import com.example.lazier.WeatherModule.dto.UserWeatherDto;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class UserWeather {

    @Id
    String userId;

    String cityName;
    String locationName;

    public UserWeather(UserWeatherDto userWeatherDto) {
        this.userId = userWeatherDto.getUserId();
        this.cityName = userWeatherDto.getCityName();
        this.locationName = userWeatherDto.getLocationName();
    }
}
