package com.example.lazier.exception.YoutubeException;

public abstract class CustomException extends RuntimeException {

  abstract public int getStatusCode();

  abstract public String getMessage();
}
