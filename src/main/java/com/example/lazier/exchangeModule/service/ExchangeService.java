package com.example.lazier.exchangeModule.service;

import com.example.lazier.exchangeModule.dto.ExchangeDto;
import java.util.List;

public interface ExchangeService {

    /**
     * 환율정보 추가 (10가지 정보)
     */
    void add(ExchangeDto exchangeDto, String userId);

    /**
     * 환율정보 조회 (10개 통화의 4가지 정보) - 사용자 조건으로
     */
    List<ExchangeDto> getExchangeList(String userId);

    /**
     * 특정 통화의 환율 상세정보 조회 - 사용자, 통화명 조건으로
     */
    ExchangeDto getExchangeCurrencyDetail(String userId, String currencyName);

    /**
     * 사용자 정보 업데이트
     */
    void updateRealTimeExchange(String userId, ExchangeDto exchangedto);

    /**
     * 사용자 정보 삭제 (모듈 닫을 경우)
     */
    void deleteExchange(String userId);

}
