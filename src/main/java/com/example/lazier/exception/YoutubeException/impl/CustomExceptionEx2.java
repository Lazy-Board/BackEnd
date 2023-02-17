package com.example.lazier.exception.YoutubeException.impl;

import com.example.lazier.exception.YoutubeException.CustomException;
import org.springframework.http.HttpStatus;

public class CustomExceptionEx2 extends CustomException {
    @Override
    public int getStatusCode() {
       return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "예제2";
    }
}
