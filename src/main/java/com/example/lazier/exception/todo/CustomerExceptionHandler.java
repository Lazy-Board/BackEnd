package com.example.lazier.exception.todo;

import com.example.lazier.controller.TodoController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = TodoController.class)
public class CustomerExceptionHandler {

	@ExceptionHandler(FailedWriteException.class)
	public ResponseEntity<ErrorMessage> failedWriteException(FailedWriteException exception) {

		return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}
}
