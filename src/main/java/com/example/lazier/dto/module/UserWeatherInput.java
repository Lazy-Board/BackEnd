package com.example.lazier.dto.module;

import lombok.Data;

@Data
public class UserWeatherInput {

    String userId;
    String cityName;
    String locationName;

}
