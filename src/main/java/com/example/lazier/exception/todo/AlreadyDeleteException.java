package com.example.lazier.exception.todo;

public class AlreadyDeleteException extends RuntimeException{
	public AlreadyDeleteException(String message) {
		super(message);
	}

}
