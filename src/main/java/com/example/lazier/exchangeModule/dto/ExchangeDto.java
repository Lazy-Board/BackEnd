package com.example.lazier.exchangeModule.dto;

import com.example.lazier.exchangeModule.persist.entity.Exchange;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDto {

    private String userId;                  // 사용자명
    private String currencyName;            // 통화명
    private String tradingStandardRate;     // 매매기준율
    private String comparedPreviousDay;     // 전일대비
    private String fluctuationRate;         // 등락율
    private String buyCash;                 // 현찰 살 때
    private String sellCash;                // 현찰 팔 때
    private String sendMoney;               // 송금 보낼 때
    private String receiveMoney;            // 송금 받을 때
    private String updateAt;                // 업데이트 날짜
    private String round;                   // 고시회차

    public static ExchangeDto to(Exchange exchange) {
        return ExchangeDto.builder()
                        .userId(exchange.getUserId())
                        .currencyName(exchange.getCurrencyName())
                        .tradingStandardRate(exchange.getTradingStandardRate())
                        .comparedPreviousDay(exchange.getComparedPreviousDay())
                        .fluctuationRate(exchange.getFluctuationRate())
                        .buyCash(exchange.getBuyCash())
                        .sellCash(exchange.getSellCash())
                        .sendMoney(exchange.getSendMoney())
                        .receiveMoney(exchange.getReceiveMoney())
                        .updateAt(exchange.getUpdateAt())
                        .round(exchange.getRound())
                        .build();
    }

    public static List<ExchangeDto> from(List<Exchange> exchanges) {

        if (exchanges == null) {
            return null;
        }

        List<ExchangeDto> exchangeDtoList = new ArrayList<>();
        for (Exchange x : exchanges) {
            exchangeDtoList.add(ExchangeDto.to(x));
        }

        return exchangeDtoList;
    }

}
