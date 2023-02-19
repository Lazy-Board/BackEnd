package com.example.lazier.stockModule.service;

import com.example.lazier.stockModule.dto.UserAllStockDto;
import com.example.lazier.stockModule.dto.UserPartialStockDto;
import com.example.lazier.stockModule.dto.UserStockInput;
import com.example.lazier.stockModule.persist.entity.UserStock;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface UserStockService {

    void add(HttpServletRequest request, UserStockInput parameter);

    List<UserAllStockDto> getUserWantedStock(HttpServletRequest request, UserStockInput stockInput);

    List<UserPartialStockDto> getUserPartialStock(HttpServletRequest request, UserStockInput stockInput);

    void update(HttpServletRequest request, UserStockInput parameter);

}
