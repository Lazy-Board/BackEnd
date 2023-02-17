package com.example.lazier.WeatherModule.persist.repository;


import com.example.lazier.WeatherModule.persist.entity.UserWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWeatherRepository extends JpaRepository<UserWeather, String> {

}
