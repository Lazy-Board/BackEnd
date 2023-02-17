package com.example.lazier.exception.user;

public class NotMatchMemberException extends RuntimeException {
    public NotMatchMemberException(String message) {
        super(message);
    }
}
