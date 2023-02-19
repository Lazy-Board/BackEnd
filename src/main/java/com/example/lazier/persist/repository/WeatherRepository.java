package com.example.lazier.persist.repository;


import com.example.lazier.persist.entity.module.Weather;
import com.example.lazier.persist.entity.user.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Optional<Weather> findFirstByLazierUserOrderByUpdatedAt(LazierUser lazierUser);
}
