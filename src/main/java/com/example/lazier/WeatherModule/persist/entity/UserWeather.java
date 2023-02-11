package com.example.lazier.WeatherModule.persist.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWeather {

    @Id
    String userId;

    String cityName;
    String locationName;

    public void updateUser(String cityName, String locationName) {
        this.cityName = cityName;
        this.locationName = locationName;
    }
}
