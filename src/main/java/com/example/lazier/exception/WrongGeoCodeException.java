package com.example.lazier.exception;

public class WrongGeoCodeException extends RuntimeException{

    public WrongGeoCodeException(String message) {
        super(message);
    }
}
