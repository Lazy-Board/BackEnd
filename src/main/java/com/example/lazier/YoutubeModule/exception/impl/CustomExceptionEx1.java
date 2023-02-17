package com.example.lazier.YoutubeModule.exception.impl;

import com.example.lazier.YoutubeModule.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CustomExceptionEx1 extends CustomException {
    @Override
    public int getStatusCode() {
       return HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return "예제1";
    }
}
