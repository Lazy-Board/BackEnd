package com.example.lazier.service.Impl;

import com.example.lazier.dto.module.ExchangeDto;
import com.example.lazier.persist.entity.module.Exchange;
import com.example.lazier.persist.repository.ExchangeRepository;
import com.example.lazier.scraper.ExchangeScraper;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final ExchangeScraper scraper;

    // 환율 정보 추가 (10개 정보)
    @Transactional
    public void add() {
        List<ExchangeDto> exchangeDto = scraper.scrap();

        for (int i = 0; i < 10; i++) {
            exchangeRepository.save(Exchange.builder()
                            .currencyName(exchangeDto.get(i).getCurrencyName())
                            .countryName(exchangeDto.get(i).getCountryName())
                            .tradingStandardRate(exchangeDto.get(i).getTradingStandardRate())
                            .comparedPreviousDay(exchangeDto.get(i).getComparedPreviousDay())
                            .fluctuationRate(exchangeDto.get(i).getFluctuationRate())
                            .buyCash(exchangeDto.get(i).getBuyCash())
                            .sellCash(exchangeDto.get(i).getSellCash())
                            .sendMoney(exchangeDto.get(i).getSendMoney())
                            .receiveMoney(exchangeDto.get(i).getReceiveMoney())
                            .updateAt(exchangeDto.get(i).getUpdateAt())
                            .round(exchangeDto.get(i).getRound())
                            .build());
        }

    }
}
