package com.example.lazier.exception;

public class WrongAddressFormException extends RuntimeException{

    public WrongAddressFormException(String message) {
        super(message);
    }
}
