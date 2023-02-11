package com.example.lazier.user.exception;

public class FailedLoginException extends RuntimeException {
    public FailedLoginException(String message) {
        super(message);
    }
}

