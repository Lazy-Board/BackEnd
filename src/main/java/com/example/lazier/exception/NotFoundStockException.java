package com.example.lazier.exception;

// 예외
public class NotFoundStockException extends RuntimeException {
    public NotFoundStockException(String message) {
        super(message);
    }
}
