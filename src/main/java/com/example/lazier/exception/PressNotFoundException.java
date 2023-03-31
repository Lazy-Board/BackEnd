package com.example.lazier.exception;

public class PressNotFoundException extends RuntimeException {
    public PressNotFoundException(String message) {
        super(message);
    }
}
