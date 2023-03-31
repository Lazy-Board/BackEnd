package com.example.lazier.exception;

// 예외
public class NotFoundExchangeException extends RuntimeException {
    public NotFoundExchangeException(String message) {
        super(message);
    }
}
