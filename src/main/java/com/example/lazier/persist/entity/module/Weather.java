package com.example.lazier.persist.entity.module;


import com.example.lazier.dto.module.WeatherDto;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private WeatherLocation weatherLocation;
    private String cityName; // 시/군/구 이름
    private String locationName; // 동 이름

    private String icon; // 날씨 아이콘

    private String description; // 날씨 상세정보
    private String engWeather; // 영어 날씨
    private String weatherId; // 날씨 아이디
    private String temperature;
    private String effectiveTemperature; // 체감 온도
    private String highestTemperature;
    private String lowestTemperature;

    private String pressure; // 기압
    private String humidity;
    private String windSpeed;
    private String windDirection;
    private String updatedAt;

    public Weather(WeatherLocation weatherLocation, WeatherDto weatherDto) {
        this.weatherLocation = weatherLocation;
        this.cityName = weatherDto.getCityName();
        this.locationName = weatherDto.getLocationName();
        this.icon = weatherDto.getIcon();
        this.description = weatherDto.getDescription();
        this.engWeather = weatherDto.getEngWeather();
        this.weatherId = weatherDto.getWeatherId();
        this.temperature = weatherDto.getTemperature();
        this.effectiveTemperature = weatherDto.getEffectiveTemperature();
        this.highestTemperature = weatherDto.getHighestTemperature();
        this.lowestTemperature = weatherDto.getLowestTemperature();
        this.pressure = weatherDto.getPressure();
        this.humidity = weatherDto.getHumidity();
        this.windSpeed = weatherDto.getWindSpeed();
        this.windDirection = weatherDto.getWindDirection();
        this.updatedAt = weatherDto.getUpdatedAt();
    }
}
