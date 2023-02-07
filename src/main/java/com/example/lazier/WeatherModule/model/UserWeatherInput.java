package com.example.lazier.WeatherModule.model;

import lombok.Data;

@Data
public class UserWeatherInput {

    String userId;
    String cityName;
    String locationName;

}
