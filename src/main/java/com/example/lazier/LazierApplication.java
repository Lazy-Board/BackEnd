package com.example.lazier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@SpringBootApplication
public class LazierApplication {

    public static void main(String[] args) {
        SpringApplication.run(LazierApplication.class, args);
    }
}