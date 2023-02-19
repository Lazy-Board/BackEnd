package com.example.lazier.stockModule.exception;

public class NotFoundStockException extends RuntimeException {

    public NotFoundStockException(String message) {
        super(message);
    }
}
