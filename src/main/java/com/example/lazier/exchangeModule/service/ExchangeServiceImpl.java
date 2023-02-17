package com.example.lazier.exchangeModule.service;

import com.example.lazier.exchangeModule.dto.ExchangeDto;
import com.example.lazier.exchangeModule.exception.NotFoundExchangeException;
import com.example.lazier.exchangeModule.exception.NotFoundUserIdException;
import com.example.lazier.exchangeModule.persist.entity.Exchange;
import com.example.lazier.exchangeModule.persist.repository.ExchangeRepository;
import com.example.lazier.exchangeModule.scraper.ExchangeScraper;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeRepository exchangeRepository;
    private final ExchangeScraper scraper;

    private final HttpServletRequest request;

    // 환율 정보 추가 (10개 정보)
    @Transactional
    public void add(ExchangeDto exchangeDto, String userId) {
        String exchangeString = scraper.scrap();

        if (userId == null) {
            throw new NotFoundUserIdException("사용자 ID를 찾을 수 없습니다.");
        }

        // 상세환율정보 화면 정보 추가 (10개)
        for (int i = 0; i < 10; i++) {
            String[] exchangeParse = exchangeString.split("\n");
            String[] exchangeData = exchangeParse[i].split(" ");

            String currencyName = exchangeData[0] + " " + exchangeData[1];
            String tradingStandardRate = exchangeData[2];
            String comparedToThePreviousDay = exchangeData[3] + " " + exchangeData[4];
            String fluctuationRate = exchangeData[5];
            String buyCash = exchangeData[6];
            String sellCash = exchangeData[7];
            String sendMoney = exchangeData[8];
            String receiveMoney = exchangeData[9];
            String updateAt = exchangeData[10];
            String round = exchangeData[11] + " " + exchangeData[12];

            ExchangeDto parameter = ExchangeDto.builder()
                                            .userId(userId)
                                            .currencyName(currencyName)
                                            .tradingStandardRate(tradingStandardRate)
                                            .comparedPreviousDay(comparedToThePreviousDay)
                                            .fluctuationRate(fluctuationRate)
                                            .buyCash(buyCash)
                                            .sellCash(sellCash)
                                            .sendMoney(sendMoney)
                                            .receiveMoney(receiveMoney)
                                            .updateAt(updateAt)
                                            .round(round)
                                            .build();

            Exchange exchange = Exchange.builder()
                                        .userId(parameter.getUserId())
                                        .currencyName(parameter.getCurrencyName())
                                        .tradingStandardRate(parameter.getTradingStandardRate())
                                        .comparedPreviousDay(parameter.getComparedPreviousDay())
                                        .fluctuationRate(parameter.getFluctuationRate())
                                        .buyCash(parameter.getBuyCash())
                                        .sellCash(parameter.getSellCash())
                                        .sendMoney(parameter.getSendMoney())
                                        .receiveMoney(parameter.getReceiveMoney())
                                        .updateAt(parameter.getUpdateAt())
                                        .round(parameter.getRound())
                                        .build();
            // DB에 저장
            exchangeRepository.save(exchange);
        }
    }

    // 통화별 리스트 조회 (10개 통화의 10가지 통화정보 조회 - 사용자 조건으로 조회)
    @Override
    @Transactional(readOnly = true)
    public List<ExchangeDto> getExchangeList(String userId) {
        if (userId == null) {
            throw new NotFoundUserIdException("사용자 ID를 찾을 수 없습니다.");
        }

        Optional<List<Exchange>> optionalExchanges
            = exchangeRepository.findByUserId(userId);

        if (!optionalExchanges.isPresent()) {
            throw new NotFoundExchangeException("정보를 찾을 수 없습니다.");
        }

        return ExchangeDto.from(optionalExchanges.get());
    }

    // 통화별 상세정보 조회 (ex. USD 미국의 10가지 정보 확인 - 사용자 조건으로 조회)
    @Override
    @Transactional(readOnly = true)
    public ExchangeDto getExchangeCurrencyDetail(String userId, String currencyName) {
        if (userId == null) {
            throw new NotFoundUserIdException("사용자 ID를 찾을 수 없습니다.");
        }

        List<Exchange> exchanges =
            exchangeRepository.findExchangesByUserIdAndCurrencyName(userId, currencyName);

        return ExchangeDto.to(exchanges.get(0));
    }

    @Override
    @Transactional
    public void updateRealTimeExchange(String userId, ExchangeDto exchangedto) {
        String exchangeString = scraper.scrap();
        userId = request.getAttribute("userId").toString();

        if (userId == null) {
            throw new NotFoundUserIdException("사용자 ID를 찾을 수 없습니다.");
        }

        // 상세환율정보 화면 정보 추가 (10개)
        for (int i = 0; i < 10; i++) {
            String[] exchangeParse = exchangeString.split("\n");
            String[] exchangeData = exchangeParse[i].split(" ");

            String currencyName = exchangeData[0] + " " + exchangeData[1];
            String tradingStandardRate = exchangeData[2];
            String comparedToThePreviousDay = exchangeData[3] + " " + exchangeData[4];
            String fluctuationRate = exchangeData[5];
            String buyCash = exchangeData[6];
            String sellCash = exchangeData[7];
            String sendMoney = exchangeData[8];
            String receiveMoney = exchangeData[9];
            String updateAt = exchangeData[10];
            String round = exchangeData[11] + " " + exchangeData[12];

            ExchangeDto parameter = ExchangeDto.builder()
                                            .userId(userId)
                                            .currencyName(currencyName)
                                            .tradingStandardRate(tradingStandardRate)
                                            .comparedPreviousDay(comparedToThePreviousDay)
                                            .fluctuationRate(fluctuationRate)
                                            .buyCash(buyCash)
                                            .sellCash(sellCash)
                                            .sendMoney(sendMoney)
                                            .receiveMoney(receiveMoney)
                                            .updateAt(updateAt)
                                            .round(round)
                                            .build();

            Exchange exchange = Exchange.builder()
                                        .userId(parameter.getUserId())
                                        .currencyName(parameter.getCurrencyName())
                                        .tradingStandardRate(parameter.getTradingStandardRate())
                                        .comparedPreviousDay(parameter.getComparedPreviousDay())
                                        .fluctuationRate(parameter.getFluctuationRate())
                                        .buyCash(parameter.getBuyCash())
                                        .sellCash(parameter.getSellCash())
                                        .sendMoney(parameter.getSendMoney())
                                        .receiveMoney(parameter.getReceiveMoney())
                                        .updateAt(parameter.getUpdateAt())
                                        .round(parameter.getRound())
                                        .build();

            // DB에 저장
            exchangeRepository.save(exchange);
        }
    }

    @Override
    @Transactional
    public void deleteExchange(String userId) {
        userId = request.getAttribute("userId").toString();
        if (userId == null) {
            throw new NotFoundUserIdException("사용자 ID를 찾을 수 없습니다.");
        }
        exchangeRepository.deleteByUserId(userId);
    }

}
