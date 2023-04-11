package com.example.lazier.config.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://35.77.78.168:5173/",
//                "http://54.248.28.248:5173/",
                "http://18.183.26.28:5173/",
                "http://ec2-35-77-78-168.ap-northeast-1.compute.amazonaws.com:5173/",
                "http://localhost:8080/",
                "http://localhost:3000/")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3000);
    }
}
