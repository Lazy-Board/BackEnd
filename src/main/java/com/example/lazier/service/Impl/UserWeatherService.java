package com.example.lazier.service.Impl;

import com.example.lazier.dto.module.UserWeatherDto;
import com.example.lazier.dto.module.UserWeatherInput;
import com.example.lazier.exception.UserAlreadyExistException;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.UserWeather;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.persist.repository.UserWeatherRepository;
import com.example.lazier.service.user.MemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserWeatherService {

    private final UserWeatherRepository userWeatherRepository;
    private final WeatherService weatherService;
    private final MemberService memberService;

    public void add(HttpServletRequest request, UserWeatherInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        // 중복 아이디 예외
        if (userWeatherRepository.existsByLazierUser(lazierUser)) {
            throw new UserAlreadyExistException("사용자 정보가 이미 존재합니다.");
        }

        UserWeather userWeather = UserWeather.builder()
            .lazierUser(lazierUser)
            .cityName(parameter.getCityName())
            .locationName(parameter.getLocationName())
            .build();

        userWeatherRepository.save(userWeather);
        // 새로 저장된 위치 날씨 정보 저장
        weatherService.add(userWeather);
    }


    public UserWeatherDto detail(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        UserWeather userWeather = userWeatherRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));
        return UserWeatherDto.of(userWeather);
    }


    public void update(HttpServletRequest request, UserWeatherInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        UserWeather userWeather = userWeatherRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userWeather.updateUser(parameter.getCityName(), parameter.getLocationName());
        // 업데이트 된 날씨 정보 저장
        weatherService.add(userWeather);
    }

    public void delete(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        UserWeather userWeather = userWeatherRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userWeatherRepository.delete(userWeather);
    }
}
