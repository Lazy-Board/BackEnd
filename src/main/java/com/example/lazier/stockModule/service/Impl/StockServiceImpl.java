package com.example.lazier.stockModule.service.Impl;

import com.example.lazier.stockModule.dto.StockDto;
import com.example.lazier.stockModule.persist.entity.Stock;
import com.example.lazier.stockModule.persist.repository.StockRepository;
import com.example.lazier.stockModule.scraper.NaverStockScraper;
import com.example.lazier.stockModule.service.StockService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    private final NaverStockScraper stockScraper;

    @Override
    public void add() {
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
}
