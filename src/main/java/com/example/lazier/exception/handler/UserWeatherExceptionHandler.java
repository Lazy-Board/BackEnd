package com.example.lazier.exception.handler;

import com.example.lazier.controller.UserWeatherController;
import com.example.lazier.exception.ErrorMessage;
import com.example.lazier.exception.LocationNotFoundException;
import com.example.lazier.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = UserWeatherController.class)
public class UserWeatherExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<ErrorMessage> locationNotFoundException(LocationNotFoundException exception) {
        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }
}
