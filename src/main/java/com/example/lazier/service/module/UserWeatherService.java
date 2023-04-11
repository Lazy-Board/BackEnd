package com.example.lazier.service.module;

import com.example.lazier.component.NaverGeocodingApi;
import com.example.lazier.dto.module.UserWeatherInput;
import com.example.lazier.dto.module.WeatherLocationDto;
import com.example.lazier.exception.LocationNotFoundException;
import com.example.lazier.exception.UserAlreadyExistException;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.persist.entity.module.UserWeather;
import com.example.lazier.persist.entity.module.WeatherLocation;
import com.example.lazier.persist.repository.UserWeatherRepository;
import com.example.lazier.persist.repository.WeatherLocationRepository;
import com.example.lazier.service.user.MyPageService;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserWeatherService {

    private final UserWeatherRepository userWeatherRepository;
    private final WeatherLocationRepository weatherLocationRepository;
    private final WeatherService weatherService;
    private final MyPageService myPageService;

    private final NaverGeocodingApi naverGeocodingApi;

    public void add(HttpServletRequest request, UserWeatherInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = myPageService.searchMember(userId);

        // 중복 아이디 예외
        if (userWeatherRepository.existsByLazierUser(lazierUser)) {
            throw new UserAlreadyExistException("사용자 정보가 이미 존재합니다.");
        }

        WeatherLocation weatherLocation = check(parameter.getCityName(),
            parameter.getLocationName());

        UserWeather userWeather = UserWeather.builder()
            .lazierUser(lazierUser)
            .weatherLocation(weatherLocation)
            .build();

        userWeatherRepository.save(userWeather);
    }


    public WeatherLocationDto detail(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = myPageService.searchMember(userId);

        UserWeather userWeather = userWeatherRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        WeatherLocation weatherLocation = weatherLocationRepository.findById(
                userWeather.getWeatherLocation().getLocationId())
            .orElseThrow(() -> new LocationNotFoundException("주소정보가 존재하지 않습니다."));

        return WeatherLocationDto.of(weatherLocation);
    }

    @Transactional
    public void update(HttpServletRequest request, UserWeatherInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = myPageService.searchMember(userId);

        UserWeather userWeather = userWeatherRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        WeatherLocation weatherLocation = check(parameter.getCityName(),
            parameter.getLocationName());

        userWeather.updateUser(weatherLocation);
//        // 업데이트 된 날씨 정보 저장
//        weatherService.add(userWeather);
    }

    @Transactional
    public void delete(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = myPageService.searchMember(userId);

        UserWeather userWeather = userWeatherRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userWeatherRepository.delete(userWeather);
    }

    // 동일한 날씨 위치정보 존재여부 확인후 해당 위치정보 반환
    public WeatherLocation check(String cityName, String locationName) {
        boolean exists = weatherLocationRepository.existsByCityNameAndLocationName(cityName,
            locationName);

        if (!exists) {

            String[] geocode = naverGeocodingApi.getGeoCode(cityName + " " + locationName).split(", ");
            String lat = geocode[1];
            String lon = geocode[0];

            weatherLocationRepository.save(WeatherLocation.builder()
                .cityName(cityName)
                .locationName(locationName)
                .lat(lat)
                .lon(lon)
                .build());
            // 새로 저장한 위치정보에 대한 날씨 정보 저장
            WeatherLocation weatherLocation = weatherLocationRepository.findByCityNameAndLocationName(
                    cityName, locationName)
                .orElseThrow(() -> new LocationNotFoundException("주소정보가 존재하지 않습니다."));
            weatherService.add(weatherLocation);
        }

        return weatherLocationRepository.findByCityNameAndLocationName(
                cityName, locationName)
            .orElseThrow(() -> new LocationNotFoundException("주소정보가 존재하지 않습니다."));
    }

}
