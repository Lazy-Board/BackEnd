package com.example.lazier.service.traffic;

import com.example.lazier.component.NaverGeocodingApi;
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

}
