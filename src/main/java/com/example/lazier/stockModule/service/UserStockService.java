package com.example.lazier.stockModule.service;

import com.example.lazier.stockModule.dto.UserAllStockDto;
import com.example.lazier.stockModule.dto.UserPartialStockDto;
import com.example.lazier.stockModule.dto.UserStockInput;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface UserStockService {

    void add(UserStockInput parameter);

    List<UserAllStockDto> getUserWantedStock(String userId);

    List<UserPartialStockDto> getUserPartialStock(String userId);

    void update(HttpServletRequest request, UserStockInput parameter);

}
