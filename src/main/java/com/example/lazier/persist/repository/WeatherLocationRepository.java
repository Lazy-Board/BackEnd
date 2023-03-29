package com.example.lazier.persist.repository;

import com.example.lazier.persist.entity.module.WeatherLocation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherLocationRepository extends JpaRepository<WeatherLocation, Long> {

    boolean existsByCityNameAndLocationName(String cityName, String locationName);

    Optional<WeatherLocation> findByCityNameAndLocationName(String cityName, String locationName);
}
