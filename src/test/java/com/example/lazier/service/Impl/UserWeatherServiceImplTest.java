package com.example.lazier.service.Impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.example.lazier.dto.module.UserWeatherDto;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.UserWeather;
import com.example.lazier.persist.repository.UserWeatherRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

@ExtendWith(MockitoExtension.class)
class UserWeatherServiceImplTest {

    @Mock
    private UserWeatherRepository userWeatherRepository;

    @InjectMocks
    private UserWeatherServiceImpl userWeatherService;

    @Test
    @DisplayName("사용자 정보 조회 성공")
    void successGetUserInfo() {
        // given
        given(userWeatherRepository.findById(anyString())).willReturn(Optional.of(UserWeather.builder().cityName("종로구").locationName("평창동").build()));
        // when
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("userId", "1234");

        UserWeatherDto userWeather = userWeatherService.detail(request);
        // then
        assertEquals("종로구", userWeather.getCityName());
        assertEquals("평창동", userWeather.getLocationName());
    }

    @Test
    @DisplayName("사용자 정보 조회 실패")
    void failGetUserInfo() {
        // given
        given(userWeatherRepository.findById("1234")).willReturn(Optional.empty());
        // when
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setAttribute("userId", "1234");

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userWeatherService.detail(request));
        // then
        assertEquals("사용자 정보가 존재하지 않습니다.", exception.getMessage());
    }
}