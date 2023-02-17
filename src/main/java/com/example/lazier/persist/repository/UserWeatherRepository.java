package com.example.lazier.persist.repository;


import com.example.lazier.persist.entity.module.UserWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWeatherRepository extends JpaRepository<UserWeather, String> {

}
