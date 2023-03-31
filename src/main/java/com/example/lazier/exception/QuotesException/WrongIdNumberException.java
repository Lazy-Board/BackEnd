package com.example.lazier.exception.QuotesException;

public class WrongIdNumberException extends RuntimeException {

    public WrongIdNumberException(String message) {
        super(message);
    }
}
