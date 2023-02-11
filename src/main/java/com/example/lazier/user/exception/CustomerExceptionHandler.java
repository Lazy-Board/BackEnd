package com.example.lazier.user.exception;

import com.example.lazier.user.controller.MemberController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = MemberController.class)
public class CustomerExceptionHandler {

    @ExceptionHandler(FailedSignUpException.class)
    public ResponseEntity<ErrorMessage> failedSignUpException(FailedSignUpException exception) {

        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }
}
