package com.example.lazier.service.module;

import com.example.lazier.component.KakaoNavigationApi;
import com.example.lazier.component.NaverGeocodingApi;
import com.example.lazier.dto.module.DurationDto;
import com.example.lazier.dto.module.TrafficDto;
import com.example.lazier.dto.module.TrafficInput;
import com.example.lazier.exception.UserAlreadyExistException;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.Traffic;
import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.persist.repository.TrafficRepository;
import com.example.lazier.service.user.MyPageService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TrafficService {

    private final TrafficRepository trafficRepository;
    private final NaverGeocodingApi naverGeocodingApi;
    private final KakaoNavigationApi kakaoNavigationApi;
    private final MyPageService myPageService;

    public void add(HttpServletRequest request, TrafficInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = myPageService.searchMember(userId);

        if (trafficRepository.existsByLazierUser(lazierUser)) {
            throw new UserAlreadyExistException("사용자 정보가 존재합니다.");
        }

        String startingGeoCode = naverGeocodingApi.getGeoCode(parameter.getStartingPoint());
        String destinationGeoCode = naverGeocodingApi.getGeoCode(parameter.getDestination());

        Traffic traffic = Traffic.builder()
            .lazierUser(lazierUser)
            .startingPoint(parameter.getStartingPoint())
            .startingGeoCode(startingGeoCode)
            .destination(parameter.getDestination())
            .destinationGeoCode(destinationGeoCode)
            .build();

        trafficRepository.save(traffic);
    }

    public TrafficDto getUserInfo(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = myPageService.searchMember(userId);

        Optional<Traffic> optionalTraffic = trafficRepository.findByLazierUser(lazierUser);

        return optionalTraffic.map(TrafficDto::of).orElse(null);
    }

    @Transactional
    public void update(HttpServletRequest request, TrafficInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = myPageService.searchMember(userId);

        Traffic traffic = trafficRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        String startingGeoCode = naverGeocodingApi.getGeoCode(parameter.getStartingPoint());
        String destinationGeoCode = naverGeocodingApi.getGeoCode(parameter.getDestination());

        traffic.update(parameter.getStartingPoint(), parameter.getDestination(), startingGeoCode,
            destinationGeoCode);
    }

    public void delete(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = myPageService.searchMember(userId);

        Traffic traffic = trafficRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        trafficRepository.delete(traffic);
    }

    public DurationDto getTrafficDuration(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = myPageService.searchMember(userId);

        Optional<Traffic> optionalTraffic = trafficRepository.findByLazierUser(lazierUser);
        if (!optionalTraffic.isPresent()) {
            return null;
        }

        Traffic traffic = optionalTraffic.get();
        String duration = kakaoNavigationApi.getDuration(traffic.getStartingGeoCode(),
            traffic.getDestinationGeoCode());

        return DurationDto.builder()
            .duration(duration)
            .startingPoint(traffic.getStartingPoint())
            .destination(traffic.getDestination())
            .build();
    }
}
