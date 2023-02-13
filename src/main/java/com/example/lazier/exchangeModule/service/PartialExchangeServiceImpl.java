package com.example.lazier.exchangeModule.service;

import com.example.lazier.exchangeModule.dto.PartialExchangeDto;
import com.example.lazier.exchangeModule.exception.NotFoundExchangeException;
import com.example.lazier.exchangeModule.exception.NotFoundUserIdException;
import com.example.lazier.exchangeModule.persist.entity.PartialExchange;
import com.example.lazier.exchangeModule.persist.repository.PartialExchangeRepository;
import com.example.lazier.exchangeModule.scraper.ExchangeScraper;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 수정
@RequiredArgsConstructor
@Service
public class PartialExchangeServiceImpl implements PartialExchangeService {
    private final PartialExchangeRepository partialExchangeRepository;

    private final HttpServletRequest request;

    private final ExchangeScraper scraper;

    @Override
    public void partialAdd(PartialExchangeDto partialExchangeDto, String userId) {
        String exchangeString = scraper.scrap();

        // 메인화면 환율 정보 추가 (4개) - 회원가입 후 모듈 선택 or 마이페이지에서 모듈 선택시 적용
        for (int i = 0; i < 4; i++) {
            String[] exchangeParse = exchangeString.split("\n");
            String[] exchangeData = exchangeParse[i].split(" ");

            String currencyName = exchangeData[0] + " " + exchangeData[1];
            String tradingStandardRate = exchangeData[2];
            String comparedToThePreviousDay = exchangeData[3] + " " + exchangeData[4];
            String fluctuationRate = exchangeData[5];

            PartialExchangeDto parameter = PartialExchangeDto.builder()
                                        .userId(userId)
                                        .currencyName(currencyName)
                                        .tradingStandardRate(tradingStandardRate)
                                        .comparedPreviousDay(comparedToThePreviousDay)
                                        .fluctuationRate(fluctuationRate)
                                        .build();

            PartialExchange partialExchange = PartialExchange.builder()
                                            .userId(parameter.getUserId())
                                            .currencyName(parameter.getCurrencyName())
                                            .tradingStandardRate(parameter.getTradingStandardRate())
                                            .comparedPreviousDay(parameter.getComparedPreviousDay())
                                            .fluctuationRate(parameter.getFluctuationRate())
                                            .build();

            partialExchangeRepository.save(partialExchange);
        }
    }

    // 메인화면의 환율정보 조회 (10개 통화의 4가지 통화정보 조회 - 사용자 조건으로 조회)
    @Override
    public List<PartialExchangeDto> getExchangePartialInfo(String userId) {
        if (userId == null) {
            throw new NotFoundUserIdException("사용자 ID를 찾을 수 없습니다.");
        }

        Optional<List<PartialExchange>> optionalPartialExchanges
            = partialExchangeRepository.findByUserId(userId);

        if (!optionalPartialExchanges.isPresent()) {
            throw new NotFoundExchangeException("정보를 찾을 수 없습니다.");
        }

        return PartialExchangeDto.from(optionalPartialExchanges.get());
    }

    @Override
    @Transactional
    public void updateRealTimePartialExchange(String userId,
        PartialExchangeDto partialExchangeDto) {

        String exchangeString = scraper.scrap();
        userId = request.getAttribute("userId").toString();

        if (userId == null) {
            throw new NotFoundUserIdException("사용자 ID를 찾을 수 없습니다.");
        }

        deletePartialExchange(userId);

        for (int i = 0; i < 4; i++) {
            String[] exchangeParse = exchangeString.split("\n");
            String[] exchangeData = exchangeParse[i].split(" ");

            String currencyName = exchangeData[0] + " " + exchangeData[1];
            String tradingStandardRate = exchangeData[2];
            String comparedToThePreviousDay = exchangeData[3] + " " + exchangeData[4];
            String fluctuationRate = exchangeData[5];

            PartialExchangeDto parameter = PartialExchangeDto.builder()
                                                    .userId(userId)
                                                    .currencyName(currencyName)
                                                    .tradingStandardRate(tradingStandardRate)
                                                    .comparedPreviousDay(comparedToThePreviousDay)
                                                    .fluctuationRate(fluctuationRate)
                                                    .build();

            PartialExchange partialExchange = PartialExchange.builder()
                                        .userId(parameter.getUserId())
                                        .currencyName(parameter.getCurrencyName())
                                        .tradingStandardRate(parameter.getTradingStandardRate())
                                        .comparedPreviousDay(parameter.getComparedPreviousDay())
                                        .fluctuationRate(parameter.getFluctuationRate())
                                        .build();

            partialExchangeRepository.save(partialExchange);
        }
    }

    @Override
    @Transactional
    public void deletePartialExchange(String userId) {
        userId = request.getAttribute("userId").toString();

        if (userId == null) {
            throw new NotFoundUserIdException("사용자 ID를 찾을 수 없습니다.");
        }

        partialExchangeRepository.deleteByUserId(userId);
    }
}
