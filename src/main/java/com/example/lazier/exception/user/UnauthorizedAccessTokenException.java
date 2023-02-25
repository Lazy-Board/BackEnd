package com.example.lazier.exception.user;

public class UnauthorizedAccessTokenException extends RuntimeException {
    public UnauthorizedAccessTokenException(String message) {
        super(message);
    }
}
