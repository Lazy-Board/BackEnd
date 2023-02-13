package com.example.lazier.exchangeModule.service;

import com.example.lazier.exchangeModule.dto.PartialExchangeDto;
import java.util.List;

// 서비스
public interface PartialExchangeService {

    /**
     * 환율정보 추가 (10개 통화의 4가지 정보 포함 -  통화명, 매매기준율, 등락율, 전일대비)
     */
    void partialAdd(PartialExchangeDto partialExchangeDto, String userId);

    /**
     * 환율 정보 조회 (10개 통화의 4가지 정보) - 사용자 조건으로
     */
    List<PartialExchangeDto> getExchangePartialInfo(String userId);

    /**
     * 환율 정보 업데이트 (10개 통화의 4가지 정보) - 사용자 조건으로
     */
    void updateRealTimePartialExchange(String userId, PartialExchangeDto partialExchangeDto);

    /**
     * 환율정보 추가 (10개 통화의 4가지 정보 포함 -  통화명, 매매기준율, 등락율, 전일대비)
     */
    void deletePartialExchange(String userId);

}
