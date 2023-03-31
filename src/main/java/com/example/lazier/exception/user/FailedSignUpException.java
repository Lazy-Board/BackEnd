package com.example.lazier.exception.user;

public class FailedSignUpException extends RuntimeException {
    public FailedSignUpException(String message) {
        super(message);
    }
}
