package com.example.lazier.service.Impl;

import com.example.lazier.dto.module.StockDto;
import com.example.lazier.persist.entity.module.Stock;
import com.example.lazier.persist.repository.StockRepository;
import com.example.lazier.scraper.NaverStockScraper;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    private final NaverStockScraper stockScraper;

    public void add() {
        delete();
        List<StockDto> stockDto = stockScraper.NaverScrap();

        for (int i = 0; i < 10; i++) {
            stockRepository.save(Stock.builder()
                                    .stockName(stockDto.get(i).getStockName())
                                    .price(stockDto.get(i).getPrice())
                                    .diffAmount(stockDto.get(i).getDiffAmount())
                                    .dayRange(stockDto.get(i).getDayRange())
                                    .marketPrice(stockDto.get(i).getMarketPrice())
                                    .highPrice(stockDto.get(i).getHighPrice())
                                    .lowPrice(stockDto.get(i).getLowPrice())
                                    .tradingVolume(stockDto.get(i).getTradingVolume())
                                    .updateAt(stockDto.get(i).getUpdateAt())
                                    .build());
        }
    }

    @Transactional
    public void delete() {
        stockRepository.deleteAll();
    }
}
