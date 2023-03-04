package com.example.lazier.persist.repository;


import com.example.lazier.persist.entity.module.Weather;
import com.example.lazier.persist.entity.module.LazierUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Optional<Weather> findTopByLazierUserOrderByUpdatedAtDesc (LazierUser lazierUser);
    List<Weather> findAllByUpdatedAtBefore(String date);
}
