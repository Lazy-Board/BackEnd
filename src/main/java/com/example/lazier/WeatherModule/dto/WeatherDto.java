package com.example.lazier.WeatherModule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {

    String userId;
    String cityName; // 시/군/구 이름
    String locationName; // 동 이름
    String temperature;
    String effectiveTemperature; // 체감 온도
    String highestTemperature;
    String lowestTemperature;
    String weatherInformation;
    String weatherComparison; // 어제 날씨 비교 온도
    String humidity;
    String ultraviolet; // 자외선
    String fineParticle; // 미세먼지
    String ultrafineParticle; // 초미세먼지
    String windSpeed;
    String windDirection;
    String updatedAt;

}
