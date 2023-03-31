package com.example.lazier.exception.user;

import com.example.lazier.controller.MemberController;
import com.example.lazier.controller.MyPageController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = {MemberController.class, MyPageController.class})
public class MemberExceptionHandler {

    @ExceptionHandler(FailedSignUpException.class)
    public ResponseEntity<ErrorMessage> failedSignUpException(FailedSignUpException exception) {

        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(FailedLoginException.class)
    public ResponseEntity<ErrorMessage> failedLoginException(FailedLoginException exception) {

        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ErrorMessage> notFoundMemberException(NotFoundMemberException exception) {

        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(NotMatchMemberException.class)
    public ResponseEntity<ErrorMessage> notMatchMemberException(NotMatchMemberException exception) {

        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(UnauthorizedRefreshTokenException.class)
    public ResponseEntity<ErrorMessage> unauthorizedRefreshTokenException(UnauthorizedRefreshTokenException exception) {

        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(UnauthorizedMemberException.class)
    public ResponseEntity<ErrorMessage> unauthorizedMemberException(UnauthorizedMemberException exception) {

        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorMessage> invalidTokenException(InvalidTokenException exception) {

        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(InvalidAccessException.class)
    public ResponseEntity<ErrorMessage> invalidAccessException(InvalidAccessException exception) {

        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(FailedUpdateException.class)
    public ResponseEntity<ErrorMessage> failedUpdateException(FailedUpdateException exception) {

        return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }
}
