package com.example.lazier.persist.repository;


import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.persist.entity.module.Weather;
import com.example.lazier.persist.entity.module.WeatherLocation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Optional<Weather> findTopByWeatherLocationOrderByUpdatedAtDesc (WeatherLocation weatherLocation);
    List<Weather> findAllByUpdatedAtBefore(String date);
//    void deleteAllByLazierUser(LazierUser lazierUser);

}
