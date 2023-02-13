package com.example.lazier.exchangeModule.dto;

import com.example.lazier.exchangeModule.persist.entity.PartialExchange;
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
public class PartialExchangeDto {
    private String userId;
    private String currencyName;            // 통화명
    private String tradingStandardRate;     // 매매기준율
    private String comparedPreviousDay;     // 전일대비
    private String fluctuationRate;         // 등락율

    public static PartialExchangeDto to(PartialExchange partialExchange) {
        return PartialExchangeDto.builder()
                                .userId(partialExchange.getUserId())
                                .currencyName(partialExchange.getCurrencyName())
                                .tradingStandardRate(partialExchange.getTradingStandardRate())
                                .comparedPreviousDay(partialExchange.getComparedPreviousDay())
                                .fluctuationRate(partialExchange.getFluctuationRate())
                                .build();
    }

    public static List<PartialExchangeDto> from(List<PartialExchange> partialExchanges) {

        if (partialExchanges == null) {
            return null;
        }

        List<PartialExchangeDto> partialExchangeDtoList = new ArrayList<>();
        for (PartialExchange x : partialExchanges) {
            partialExchangeDtoList.add(PartialExchangeDto.to(x));
        }

        return partialExchangeDtoList;
    }

}
