package com.example.lazier.QuotesModule.exception.handler;

import com.example.lazier.QuotesModule.controller.QuotesController;
import com.example.lazier.QuotesModule.exception.ErrorMessage;
import com.example.lazier.QuotesModule.exception.WrongIdNumberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = QuotesController.class)
public class QuotesExceptionHandler {

    @ExceptionHandler(WrongIdNumberException.class)
    public ResponseEntity<ErrorMessage> wrongIdNumberException(WrongIdNumberException exception) {
        return ResponseEntity.internalServerError()
            .body(ErrorMessage.of(exception, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
