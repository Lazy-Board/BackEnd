package com.example.lazier.YoutubeModule.exception;

public abstract class CustomException extends RuntimeException {

  abstract public int getStatusCode();

  abstract public String getMessage();
}
