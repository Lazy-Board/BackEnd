package com.example.lazier.stockModule.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.lazier.stockModule.dto.UserAllStockDto;
import com.example.lazier.stockModule.dto.UserPartialStockDto;
import com.example.lazier.stockModule.persist.repository.UserStockRepository;
import com.example.lazier.stockModule.service.UserStockService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StockController.class)
class StockControllerTest {

    @MockBean
    private UserStockService userStockService;

    @MockBean
    private UserStockRepository userStockRepository;

    @Autowired
    private MockMvc mvc;

    @DisplayName("주식 일부 정보 조회")
    @Test
    void successGetUserPartialStock() throws Exception {
        List<UserPartialStockDto> userPartialStockDtoList = new ArrayList<>();

        String stockName = "삼성전자";
        String price = "62,600";
        String diffAmount = "▼ 1,100";
        String dayRange = "-1.73%";
        String updateAt = "2023.02.18 19:50:02";

        //given
        UserPartialStockDto userPartialStockDto = UserPartialStockDto.builder()
                                                    .stockName(stockName)
                                                    .price(price)
                                                    .diffAmount(diffAmount)
                                                    .dayRange(dayRange)
                                                    .updateAt(updateAt)
                                                    .build();

        userPartialStockDtoList.add(userPartialStockDto);

        //when
        //then
        mvc.perform(get("/stock/search"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].stockName").value(stockName))
                .andExpect(jsonPath("$[0].price").value(price))
                .andExpect(jsonPath("$[0].diffAmount").value(diffAmount))
                .andExpect(jsonPath("$[0].dayRange").value(dayRange))
                .andExpect(jsonPath("$[0].updateAt").value(updateAt));
    }

    @DisplayName("주식 상세 정보 조회")
    @Test
    void successGetUserAllStock() throws Exception {
        List<UserAllStockDto> userAllStockDtoList = new ArrayList<>();

        String stockName = "삼성전자";
        String price = "62,600";
        String diffAmount = "▼ 1,100";
        String dayRange = "-1.73%";
        String highPrice = "63,300";
        String lowPrice = "62,400";
        String marketPrice = "62,900";
        String tradingVolume = "10,675,131";
        String updateAt = "2023.02.18 19:50:02";

        //given
        UserAllStockDto userAllStockDto = UserAllStockDto.builder()
                                                        .stockName(stockName)
                                                        .price(price)
                                                        .diffAmount(diffAmount)
                                                        .dayRange(dayRange)
                                                        .highPrice(highPrice)
                                                        .lowPrice(lowPrice)
                                                        .marketPrice(marketPrice)
                                                        .tradingVolume(tradingVolume)
                                                        .updateAt(updateAt)
                                                        .build();
        userAllStockDtoList.add(userAllStockDto);

        //when
        //then
        mvc.perform(get("/stock/detail"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].stockName").value(stockName))
            .andExpect(jsonPath("$[0].price").value(price))
            .andExpect(jsonPath("$[0].diffAmount").value(diffAmount))
            .andExpect(jsonPath("$[0].dayRange").value(dayRange))
            .andExpect(jsonPath("$[0].highPrice").value(highPrice))
            .andExpect(jsonPath("$[0].lowPrice").value(lowPrice))
            .andExpect(jsonPath("$[0].marketPrice").value(marketPrice))
            .andExpect(jsonPath("$[0].tradingVolume").value(tradingVolume))
            .andExpect(jsonPath("$[0].updateAt").value(updateAt));

    }

}