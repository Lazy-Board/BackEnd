package com.example.lazier.service.module;

import static com.example.lazier.type.CurrencyName.AUD;
import static com.example.lazier.type.CurrencyName.CAD;
import static com.example.lazier.type.CurrencyName.CHF;
import static com.example.lazier.type.CurrencyName.CNY;
import static com.example.lazier.type.CurrencyName.EUR;
import static com.example.lazier.type.CurrencyName.GBP;
import static com.example.lazier.type.CurrencyName.HKD;
import static com.example.lazier.type.CurrencyName.JPY;
import static com.example.lazier.type.CurrencyName.NZD;
import static com.example.lazier.type.CurrencyName.USD;

import com.example.lazier.dto.module.UserAllExchangeDto;
import com.example.lazier.dto.module.UserExchangeInput;
import com.example.lazier.dto.module.UserPartialExchangeDto;
import com.example.lazier.exception.UserAlreadyExistException;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.Exchange;
import com.example.lazier.persist.entity.module.UserExchange;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.persist.repository.ExchangeRepository;
import com.example.lazier.persist.repository.UserExchangeRepository;
import com.example.lazier.service.user.MemberService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserExchangeService {

    private final ExchangeService exchangeService;
    private final MemberService memberService;
    private final UserExchangeRepository userExchangeRepository;
    private final ExchangeRepository exchangeRepository;

    @Transactional
    public void add(HttpServletRequest request, UserExchangeInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        if (userExchangeRepository.existsByLazierUser(lazierUser)) {
            throw new UserAlreadyExistException("사용자 정보가 이미 존재합니다.");
        }

        UserExchange userExchange = UserExchange.builder()
                                                .lazierUser(lazierUser)
                                                .usd(parameter.getUsd() + USD)
                                                .jpy(parameter.getJpy() + JPY)
                                                .eur(parameter.getEur() + EUR)
                                                .cny(parameter.getCny() + CNY)
                                                .aud(parameter.getAud() + AUD)
                                                .cad(parameter.getCad() + CAD)
                                                .chf(parameter.getChf() + CHF)
                                                .nzd(parameter.getNzd() + NZD)
                                                .hkd(parameter.getHkd() + HKD)
                                                .gbp(parameter.getGbp() + GBP)
                                                .build();
        userExchangeRepository.save(userExchange);
        exchangeService.add();
    }

    @Transactional
    public void update(HttpServletRequest request, UserExchangeInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        UserExchange userExchange = userExchangeRepository.findByLazierUser(lazierUser)
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

    public List<UserAllExchangeDto> getUserWantedExchange(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        List<UserAllExchangeDto> userAllExchangeDtoList = new ArrayList<>();

        UserExchange userExchange = userExchangeRepository.findByLazierUser(lazierUser)
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

    public List<UserPartialExchangeDto> getUserPartialExchange(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        List<UserPartialExchangeDto> userPartialExchangeDtoList = new ArrayList<>();

        UserExchange userExchange = userExchangeRepository.findByLazierUser(lazierUser)
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
