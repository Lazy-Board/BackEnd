package com.example.lazier.WeatherModule.persist.entity;


import com.example.lazier.WeatherModule.dto.WeatherDto;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String cityName; // 시/군/구 이름
    private String locationName; // 동 이름
    private String temperature;
    private String effectiveTemperature; // 체감 온도
    private String highestTemperature;
    private String lowestTemperature;
    private String weatherInformation;
    private String weatherComparison; // 어제 날씨 비교 온도
    private String humidity;
    private String ultraviolet; // 자외선
    private String fineParticle; // 미세먼지
    private String ultrafineParticle; // 초미세먼지
    private String windSpeed;
    private String windDirection;
    private String updatedAt;

    public Weather(WeatherDto weatherDto) {
        this.userId = weatherDto.getUserId();
        this.cityName = weatherDto.getCityName();
        this.locationName = weatherDto.getLocationName();
        this.temperature = weatherDto.getTemperature();
        this.effectiveTemperature = weatherDto.getEffectiveTemperature();
        this.highestTemperature = weatherDto.getHighestTemperature();
        this.lowestTemperature = weatherDto.getLowestTemperature();
        this.weatherInformation = weatherDto.getWeatherInformation();
        this.weatherComparison = weatherDto.getWeatherComparison();
        this.humidity = weatherDto.getHumidity();
        this.ultraviolet = weatherDto.getUltraviolet();
        this.fineParticle = weatherDto.getFineParticle();
        this.ultrafineParticle = weatherDto.getUltrafineParticle();
        this.windSpeed = weatherDto.getWindSpeed();
        this.windDirection = weatherDto.getWindDirection();
        this.updatedAt = weatherDto.getUpdatedAt();
    }
}