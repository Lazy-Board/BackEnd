package com.example.lazier.WeatherModule.exception.handler;

import com.example.lazier.WeatherModule.controller.WeatherController;
import com.example.lazier.WeatherModule.exception.AddressNotFoundException;
import com.example.lazier.WeatherModule.exception.ErrorMessage;
import com.example.lazier.WeatherModule.exception.UserAlreadyExistException;
import com.example.lazier.WeatherModule.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = WeatherController.class)
public class WeatherExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> userNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorMessage> userAlreadyExistException(
        UserAlreadyExistException exception) {
        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorMessage> addressNotFoundException(
        AddressNotFoundException exception) {
        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }
}
