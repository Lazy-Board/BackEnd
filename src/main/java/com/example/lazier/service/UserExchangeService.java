package com.example.lazier.service;


import com.example.lazier.dto.module.UserAllExchangeDto;
import com.example.lazier.dto.module.UserPartialExchangeDto;
import com.example.lazier.dto.module.UserExchangeInput;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface UserExchangeService {

    void add(UserExchangeInput parameter);

    List<UserAllExchangeDto> getUserWantedExchange(String userId);

    List<UserPartialExchangeDto> getUserPartialExchange(String userId);

    void update(HttpServletRequest request, UserExchangeInput parameter);

}
