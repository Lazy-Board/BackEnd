package com.example.lazier.exception;

public class NotFoundUserIdException extends RuntimeException {
    public NotFoundUserIdException(String message) {
        super(message);
    }
}