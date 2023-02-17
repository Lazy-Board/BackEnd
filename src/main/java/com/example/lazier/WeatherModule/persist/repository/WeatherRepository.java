package com.example.lazier.WeatherModule.persist.repository;


import com.example.lazier.WeatherModule.persist.entity.Weather;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, String> {

    Optional<Weather> findFirstByUserIdOrderByUpdatedAt(String userId);
}
