package com.example.lazier.exchangeModule.exception;

public class NotFoundUserIdException extends RuntimeException {
    public NotFoundUserIdException(String message) {
        super(message);
    }
}