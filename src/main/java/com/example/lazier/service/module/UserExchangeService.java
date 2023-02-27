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
import com.example.lazier.exception.NotFoundExchangeException;
import com.example.lazier.exception.UserAlreadyExistException;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.DetailExchange;
import com.example.lazier.persist.entity.module.Exchange;
import com.example.lazier.persist.entity.module.UserExchange;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.persist.repository.DetailExchangeRepository;
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
    private final DetailExchangeRepository detailExchangeRepository;
    private final ExchangeRepository exchangeRepository;

    @Transactional
    public void add(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        if (userExchangeRepository.existsByLazierUser(lazierUser)) {
            throw new UserAlreadyExistException("사용자 정보가 이미 존재합니다.");
        }

        UserExchange userExchange = UserExchange.builder()
                                                .lazierUser(lazierUser)
                                                .usd(String.valueOf(USD))
                                                .jpy(String.valueOf(JPY))
                                                .eur(String.valueOf(EUR))
                                                .cny(String.valueOf(CNY))
                                                .aud("N")
                                                .cad("N")
                                                .chf("N")
                                                .nzd("N")
                                                .hkd("N")
                                                .gbp("N")
                                                .build();

        DetailExchange detailexchange = DetailExchange.builder()
                                                    .lazierUser(lazierUser)
                                                    .usd(String.valueOf(USD))
                                                    .jpy(String.valueOf(JPY))
                                                    .eur(String.valueOf(EUR))
                                                    .cny(String.valueOf(CNY))
                                                    .aud(String.valueOf(AUD))
                                                    .cad(String.valueOf(CAD))
                                                    .chf(String.valueOf(CHF))
                                                    .nzd(String.valueOf(NZD))
                                                    .hkd(String.valueOf(HKD))
                                                    .gbp(String.valueOf(GBP))
                                                    .build();

        userExchangeRepository.save(userExchange);
        detailExchangeRepository.save(detailexchange);
        exchangeService.add();
    }

    @Transactional
    public void update(HttpServletRequest request, UserExchangeInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        UserExchange userExchange = userExchangeRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userExchange.setUsd(parameter.getUsd());
        userExchange.setJpy(parameter.getJpy());
        userExchange.setEur(parameter.getEur());
        userExchange.setCny(parameter.getCny());
        userExchange.setHkd(parameter.getHkd());
        userExchange.setGbp(parameter.getGbp());
        userExchange.setChf(parameter.getChf());
        userExchange.setCad(parameter.getCad());
        userExchange.setAud(parameter.getAud());
        userExchange.setNzd(parameter.getNzd());

        userExchangeRepository.save(userExchange);
    }

    public List<UserAllExchangeDto> getExchange(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        List<UserAllExchangeDto> userAllExchangeDtoList = new ArrayList<>();

        DetailExchange detailExchange = detailExchangeRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        String[] checkList = {detailExchange.getUsd(), detailExchange.getJpy(),
                                detailExchange.getEur(), detailExchange.getCny(),
                                detailExchange.getHkd(), detailExchange.getGbp(),
                                detailExchange.getChf(), detailExchange.getCad(),
                                detailExchange.getAud(), detailExchange.getNzd()};

        for (int i = 0; i < 10; i++) {
            String currencyName = checkList[i];

            Exchange exchange = exchangeRepository.findByCurrencyNameOrderByUpdateAtDescCountryNameDesc(currencyName)
                .orElseThrow(() -> new NotFoundExchangeException("선택한 환율 종목이 있는지 확인하세요."));

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
        return userAllExchangeDtoList;
    }

    public List<UserPartialExchangeDto> getPartialExchange(HttpServletRequest request) {
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
            if (checkList[i].length() > 1) {
                String currencyName = checkList[i];

                Exchange exchange = exchangeRepository.findByCurrencyNameOrderByUpdateAtDescCountryNameDesc(currencyName)
                    .orElseThrow(() -> new NotFoundExchangeException("선택한 환율 종목이 있는지 확인하세요."));

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
