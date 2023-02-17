package com.example.lazier.persist.repository;


import com.example.lazier.persist.entity.module.Weather;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, String> {

    Optional<Weather> findFirstByUserIdOrderByUpdatedAt(String userId);
}
