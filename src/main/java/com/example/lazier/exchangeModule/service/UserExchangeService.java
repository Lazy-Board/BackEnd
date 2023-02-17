package com.example.lazier.exchangeModule.service;


import com.example.lazier.exchangeModule.dto.UserAllExchangeDto;
import com.example.lazier.exchangeModule.dto.UserPartialExchangeDto;
import com.example.lazier.exchangeModule.model.UserExchangeInput;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface UserExchangeService {

    void add(UserExchangeInput parameter);

    List<UserAllExchangeDto> getUserWantedExchange(String userId);

    List<UserPartialExchangeDto> getUserPartialExchange(String userId);

    void update(HttpServletRequest request, UserExchangeInput parameter);

}
