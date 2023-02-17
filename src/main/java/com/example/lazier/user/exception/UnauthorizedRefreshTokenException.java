package com.example.lazier.user.exception;

public class UnauthorizedRefreshTokenException extends RuntimeException {
    public UnauthorizedRefreshTokenException(String message) {
        super(message);
    }
}
