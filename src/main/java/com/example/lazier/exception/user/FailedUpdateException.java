package com.example.lazier.exception.user;

public class FailedUpdateException extends RuntimeException {
	public FailedUpdateException(String message) {
		super(message);
	}
}
