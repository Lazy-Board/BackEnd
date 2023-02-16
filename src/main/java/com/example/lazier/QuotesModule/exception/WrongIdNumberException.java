package com.example.lazier.QuotesModule.exception;

public class WrongIdNumberException extends RuntimeException {

    public WrongIdNumberException(String message) {
        super(message);
    }
}
