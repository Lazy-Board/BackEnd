package com.example.lazier.persist.repository;


import com.example.lazier.persist.entity.module.UserWeather;
import com.example.lazier.persist.entity.user.LazierUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserWeatherRepository extends JpaRepository<UserWeather, Long> {

    Optional<UserWeather> findByLazierUser(LazierUser lazierUser);

    boolean existsByLazierUser(LazierUser lazierUser);
}
