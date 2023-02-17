package com.example.lazier.user.exception;

public class FailedSignUpException extends RuntimeException {
    public FailedSignUpException(String message) {
        super(message);
    }
}
