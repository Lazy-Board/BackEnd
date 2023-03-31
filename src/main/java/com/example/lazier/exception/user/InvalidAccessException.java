package com.example.lazier.exception.user;

public class InvalidAccessException extends RuntimeException {
	public InvalidAccessException(String message) {
		super(message);
	}
}
