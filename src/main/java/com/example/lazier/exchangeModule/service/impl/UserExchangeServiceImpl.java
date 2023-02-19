package com.example.lazier.exchangeModule.service.impl;

import static com.example.lazier.exchangeModule.type.CurrencyName.AUD;
import static com.example.lazier.exchangeModule.type.CurrencyName.CAD;
import static com.example.lazier.exchangeModule.type.CurrencyName.CHF;
import static com.example.lazier.exchangeModule.type.CurrencyName.CNY;
import static com.example.lazier.exchangeModule.type.CurrencyName.EUR;
import static com.example.lazier.exchangeModule.type.CurrencyName.GBP;
import static com.example.lazier.exchangeModule.type.CurrencyName.HKD;
import static com.example.lazier.exchangeModule.type.CurrencyName.JPY;
import static com.example.lazier.exchangeModule.type.CurrencyName.NZD;
import static com.example.lazier.exchangeModule.type.CurrencyName.USD;

import com.example.lazier.exchangeModule.dto.UserAllExchangeDto;
import com.example.lazier.exchangeModule.dto.UserPartialExchangeDto;
import com.example.lazier.exchangeModule.exception.NotFoundExchangeException;
import com.example.lazier.exchangeModule.exception.UserAlreadyExistException;
import com.example.lazier.exchangeModule.exception.UserNotFoundException;
import com.example.lazier.exchangeModule.model.UserExchangeInput;
import com.example.lazier.exchangeModule.persist.entity.Exchange;
import com.example.lazier.exchangeModule.persist.entity.UserExchange;
import com.example.lazier.exchangeModule.persist.repository.ExchangeRepository;
import com.example.lazier.exchangeModule.persist.repository.UserExchangeRepository;
import com.example.lazier.exchangeModule.service.ExchangeService;
import com.example.lazier.exchangeModule.service.UserExchangeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserExchangeServiceImpl implements UserExchangeService {

    private final ExchangeService exchangeService;

    private final UserExchangeRepository userExchangeRepository;

    private final ExchangeRepository exchangeRepository;

    @Override
    @Transactional
    public void add(HttpServletRequest request, UserExchangeInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);

        if (userExchangeRepository.existsById(parameter.getUserId())) {
            throw new UserAlreadyExistException("사용자 정보가 이미 존재합니다.");
        }

        UserExchange userExchange = UserExchange.builder()
                                                .userId(parameter.getUserId())
                                                .usd(parameter.getUsd() + USD)
                                                .jpy(parameter.getJpy() + JPY)
                                                .eur(parameter.getEur() + EUR)
                                                .cny(parameter.getCny() + CNY)
                                                .aud(parameter.getAud() + AUD)
                                                .cad(parameter.getCad() + CAD)
                                                .chf(parameter.getChf() + CHF)
                                                .nzd(parameter.getNzd() + NZD)
                                                .hkd(parameter.getHkd() + HKD)
                                                .build();
        userExchangeRepository.save(userExchange);
        exchangeService.add();
    }

    @Transactional
    @Override
    public void update(HttpServletRequest request, UserExchangeInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);

        UserExchange userExchange = userExchangeRepository.findById(parameter.getUserId())
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userExchange.setUsd(parameter.getUsd() + USD);
        userExchange.setJpy(parameter.getJpy() + JPY);
        userExchange.setEur(parameter.getEur() + EUR);
        userExchange.setCny(parameter.getCny() + CNY);
        userExchange.setHkd(parameter.getHkd() + HKD);
        userExchange.setGbp(parameter.getGbp() + GBP);
        userExchange.setChf(parameter.getChf() + CHF);
        userExchange.setCad(parameter.getCad() + CAD);
        userExchange.setAud(parameter.getAud() + AUD);
        userExchange.setNzd(parameter.getNzd() + NZD);

        userExchangeRepository.save(userExchange);
    }

    @Override
    public List<UserAllExchangeDto> getUserWantedExchange(HttpServletRequest request,
        UserExchangeInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);
        List<UserAllExchangeDto> userAllExchangeDtoList = new ArrayList<>();

        UserExchange userExchange = userExchangeRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        String[] checkList = {userExchange.getUsd(), userExchange.getJpy(), userExchange.getEur(),
                            userExchange.getCny(), userExchange.getHkd(), userExchange.getGbp(),
                            userExchange.getChf(), userExchange.getCad(), userExchange.getAud(),
                            userExchange.getNzd()};

        for (int i = 0; i < 10; i++) {
            if (checkList[i].charAt(0) == 'Y') {
                String currencyName = checkList[i].substring(1);

                Exchange exchange = exchangeRepository.findByCurrencyNameOrderByUpdateAtDescCountryNameDesc(currencyName)
                    .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

                UserAllExchangeDto userAllExchangeDto = UserAllExchangeDto.builder()
                                            .currencyName(exchange.getCurrencyName())
                                            .countryName(exchange.getCountryName())
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

                userAllExchangeDtoList.add(userAllExchangeDto);
            }
        }
        return userAllExchangeDtoList;
    }

    @Override
    public List<UserPartialExchangeDto> getUserPartialExchange(HttpServletRequest request,
        UserExchangeInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);
        List<UserPartialExchangeDto> userPartialExchangeDtoList = new ArrayList<>();

        UserExchange userExchange = userExchangeRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        String[] checkList = {userExchange.getUsd(), userExchange.getJpy(), userExchange.getEur(),
                            userExchange.getCny(), userExchange.getHkd(), userExchange.getGbp(),
                            userExchange.getChf(), userExchange.getCad(), userExchange.getAud(),
                            userExchange.getNzd()};

        for (int i = 0; i < 10; i++) {
            if (checkList[i].charAt(0) == 'Y') {
                String currencyName = checkList[i].substring(1);

                Exchange exchange = exchangeRepository.findByCurrencyNameOrderByUpdateAtDescCountryNameDesc(currencyName)
                    .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

                UserPartialExchangeDto userPartialExchangeDto = UserPartialExchangeDto.builder()
                                        .currencyName(exchange.getCurrencyName())
                                        .countryName(exchange.getCountryName())
                                        .tradingStandardRate(exchange.getTradingStandardRate())
                                        .comparedPreviousDay(exchange.getComparedPreviousDay())
                                        .fluctuationRate(exchange.getFluctuationRate())
                                        .build();

                userPartialExchangeDtoList.add(userPartialExchangeDto);
            }
        }
        return userPartialExchangeDtoList;
    }
}
