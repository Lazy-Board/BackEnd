package com.example.lazier.service.traffic;

import com.example.lazier.component.KakaoNavigationApi;
import com.example.lazier.component.NaverGeocodingApi;
import com.example.lazier.dto.traffic.DurationDto;
import com.example.lazier.dto.traffic.TrafficDto;
import com.example.lazier.dto.traffic.TrafficInput;
import com.example.lazier.persist.entity.traffic.Traffic;
import com.example.lazier.persist.repository.traffic.TrafficRepository;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrafficService {

    private final TrafficRepository trafficRepository;
    private final NaverGeocodingApi naverGeocodingApi;
    private final KakaoNavigationApi kakaoNavigationApi;


    public void add(HttpServletRequest request, TrafficInput parameter) {
        parameter.setUserId((String) request.getAttribute("userId"));

        if (trafficRepository.existsById(parameter.getUserId())) {
            throw new RuntimeException("사용자 정보가 존재합니다.");
        }

        String startingGeoCode = naverGeocodingApi.getGeoCode(parameter.getStartingPoint());
        String destinationGeoCode = naverGeocodingApi.getGeoCode(parameter.getDestination());

        Traffic traffic = Traffic.builder()
            .userId(parameter.getUserId())
            .startingPoint(parameter.getStartingPoint())
            .startingGeoCode(startingGeoCode)
            .destination(parameter.getDestination())
            .destinationGeoCode(destinationGeoCode)
            .build();

        trafficRepository.save(traffic);
    }

    public TrafficDto getUserInfo(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        Traffic traffic = trafficRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("사용자 정보가 존재하지 않습니다."));

        return TrafficDto.of(traffic);
    }

    public void update(HttpServletRequest request, TrafficInput parameter) {
        parameter.setUserId((String) request.getAttribute("userId"));

        Traffic traffic = trafficRepository.findById(parameter.getUserId())
            .orElseThrow(() -> new RuntimeException("사용자 정보가 존재하지 않습니다."));

        String startingGeoCode = naverGeocodingApi.getGeoCode(parameter.getStartingPoint());
        String destinationGeoCode = naverGeocodingApi.getGeoCode(parameter.getDestination());

        traffic.update(parameter.getStartingPoint(), parameter.getDestination(), startingGeoCode,
            destinationGeoCode);
    }

    public void delete(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        Traffic traffic = trafficRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("사용자 정보가 존재하지 않습니다."));

        trafficRepository.delete(traffic);
    }

    public DurationDto getTrafficDuration(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        Traffic traffic = trafficRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("사용자 정보가 존재하지 않습니다."));

        String duration = kakaoNavigationApi.getDuration(traffic.getStartingGeoCode(),
            traffic.getDestinationGeoCode());

        return DurationDto.builder()
            .duration(duration)
            .startingPoint(traffic.getStartingPoint())
            .destination(traffic.getDestination())
            .build();
    }
}
