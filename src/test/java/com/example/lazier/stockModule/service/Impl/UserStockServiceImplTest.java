package com.example.lazier.stockModule.service.Impl;

import static com.example.lazier.stockModule.type.StockName.LG전자;
import static com.example.lazier.stockModule.type.StockName.NAVER;
import static com.example.lazier.stockModule.type.StockName.SK하이닉스;
import static com.example.lazier.stockModule.type.StockName.기아;
import static com.example.lazier.stockModule.type.StockName.삼성SDI;
import static com.example.lazier.stockModule.type.StockName.삼성전자;
import static com.example.lazier.stockModule.type.StockName.카카오;
import static com.example.lazier.stockModule.type.StockName.카카오뱅크;
import static com.example.lazier.stockModule.type.StockName.하이브;
import static com.example.lazier.stockModule.type.StockName.현대차;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.lazier.stockModule.persist.entity.Stock;
import com.example.lazier.stockModule.persist.entity.UserStock;
import com.example.lazier.stockModule.persist.repository.StockRepository;
import com.example.lazier.stockModule.persist.repository.UserStockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserStockServiceImplTest {

    @Mock
    private UserStockRepository userStockRepository;

    @Mock
    private StockRepository stockRepository;


    @DisplayName("주식 종목 선택값 저장")
    @Test
    void add() {
        //given
        UserStock userStock = UserStock.builder()
                                        .userId("haha")
                                        .samsungElectronic("Y" + 삼성전자)
                                        .skHynix("Y" + SK하이닉스)
                                        .naver("Y" + NAVER)
                                        .kakao("Y" + 카카오)
                                        .hyundaiCar("N" + 현대차)
                                        .kia("N" + 기아)
                                        .lgElectronic("N" + LG전자)
                                        .kakaoBank("N" + 카카오뱅크)
                                        .samsungSdi("N" + 삼성SDI)
                                        .hive("N" + 하이브)
                                        .build();

        //when
        //then
        userStockRepository.save(userStock);
    }

    @DisplayName("주식 정보 조회")
    @Test
    void getStock() {
        //given
        String stockName = "삼성전자";
        String price = "62,600";
        String diffAmount = "▼ 1,100";
        String dayRange = "-1.73%";
        String highPrice = "63,300";
        String lowPrice = "62,400";
        String marketPrice = "62,900";
        String tradingVolume = "10,675,131";
        String updateAt = "2023.02.18 19:50:02";

        //when
        Stock stock = Stock.builder()
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

        stockRepository.save(stock);

        //then
        assertEquals(stock.getStockName(), stockName);
        assertEquals(stock.getPrice(), price);
        assertEquals(stock.getDiffAmount(), diffAmount);
        assertEquals(stock.getDayRange(), dayRange);
        assertEquals(stock.getHighPrice(), highPrice);
        assertEquals(stock.getLowPrice(), lowPrice);
        assertEquals(stock.getMarketPrice(), marketPrice);
        assertEquals(stock.getTradingVolume(), tradingVolume);
        assertEquals(stock.getUpdateAt(), updateAt);
    }

}