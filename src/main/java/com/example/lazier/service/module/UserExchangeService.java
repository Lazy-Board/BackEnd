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

import com.example.lazier.dto.module.UpdateExchangeDto;
import com.example.lazier.dto.module.UserAllExchangeDto;
import com.example.lazier.dto.module.UserPartialExchangeDto;
import com.example.lazier.exception.NotFoundExchangeException;
import com.example.lazier.exception.UserAlreadyExistException;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.DetailExchange;
import com.example.lazier.persist.entity.module.Exchange;
import com.example.lazier.persist.entity.module.UpdateExchange;
import com.example.lazier.persist.entity.module.UserExchange;
import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.persist.repository.DetailExchangeRepository;
import com.example.lazier.persist.repository.ExchangeRepository;
import com.example.lazier.persist.repository.UpdateExchangeRepository;
import com.example.lazier.persist.repository.UserExchangeRepository;
import com.example.lazier.service.user.MyPageService;
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
    private final MyPageService myPageService;
    private final UserExchangeRepository userExchangeRepository;
    private final DetailExchangeRepository detailExchangeRepository;

    private final UpdateExchangeRepository updateExchangeRepository;
    private final ExchangeRepository exchangeRepository;

    @Transactional
    public void add(String paramId) {
        long userId = Long.parseLong(paramId);
        LazierUser lazierUser = myPageService.searchMember(userId);

        if (!userExchangeRepository.existsByLazierUser(lazierUser)) {

            UserExchange userExchange = UserExchange.builder()
                .lazierUser(lazierUser)
                .usd(String.valueOf(USD))
                .jpy(String.valueOf(JPY))
                .eur(String.valueOf(EUR))
                .cny(String.valueOf(CNY))
                .aud("X")
                .cad("X")
                .chf("X")
                .nzd("X")
                .hkd("X")
                .gbp("X")
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
    }

    @Transactional
    public void update(HttpServletRequest request, UpdateExchangeDto updateExchangeDto) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = myPageService.searchMember(userId);

        if (updateExchangeRepository.existsByLazierUser(lazierUser)) {
            UpdateExchange updateExchange = updateExchangeRepository.findByLazierUser(lazierUser)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

            updateExchange.setLazierUser(lazierUser);
            updateExchange.setCurrencyName(updateExchangeDto.getCurrencyName());

            if (updateExchange.getCurrencyName().contains("USD")) {
                updateExchange.setUsd(updateExchange.getCurrencyName());
            } else if (updateExchange.getCurrencyName().contains("JPY")) {
                updateExchange.setJpy(updateExchange.getCurrencyName());
            } else if (updateExchange.getCurrencyName().contains("EUR")) {
                updateExchange.setEur(updateExchange.getCurrencyName());
            } else if (updateExchange.getCurrencyName().contains("CNY")) {
                updateExchange.setCny(updateExchange.getCurrencyName());
            } else if (updateExchange.getCurrencyName().contains("AUD")) {
                updateExchange.setAud(updateExchange.getCurrencyName());
            } else if (updateExchange.getCurrencyName().contains("CAD")) {
                updateExchange.setCad(updateExchange.getCurrencyName());
            } else if (updateExchange.getCurrencyName().contains("CHF")) {
                updateExchange.setChf(updateExchange.getCurrencyName());
            } else if (updateExchange.getCurrencyName().contains("NZD")) {
                updateExchange.setNzd(updateExchange.getCurrencyName());
            } else if (updateExchange.getCurrencyName().contains("HKD")) {
                updateExchange.setHkd(updateExchange.getCurrencyName());
            } else if (updateExchange.getCurrencyName().contains("GBP")) {
                updateExchange.setGbp(updateExchange.getCurrencyName());
            }

            updateExchangeRepository.save(updateExchange);
        } else {
            UpdateExchange updateExchange = UpdateExchange.builder()
                .lazierUser(lazierUser)
                .currencyName(updateExchangeDto.getCurrencyName())
                .build();
            updateExchangeRepository.save(updateExchange);
        }

        UpdateExchange updateExchange = updateExchangeRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        UserExchange userExchange = userExchangeRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        if (updateExchange.getCurrencyName().contains("USD")) {
            userExchange.setUsd(updateExchange.getCurrencyName());
        } else if (updateExchange.getCurrencyName().contains("JPY")) {
            userExchange.setJpy(updateExchange.getCurrencyName());
        } else if (updateExchange.getCurrencyName().contains("EUR")) {
            userExchange.setEur(updateExchange.getCurrencyName());
        } else if (updateExchange.getCurrencyName().contains("CNY")) {
            userExchange.setCny(updateExchange.getCurrencyName());
        } else if (updateExchange.getCurrencyName().contains("AUD")) {
            userExchange.setAud(updateExchange.getCurrencyName());
        } else if (updateExchange.getCurrencyName().contains("CAD")) {
            userExchange.setCad(updateExchange.getCurrencyName());
        } else if (updateExchange.getCurrencyName().contains("CHF")) {
            userExchange.setChf(updateExchange.getCurrencyName());
        } else if (updateExchange.getCurrencyName().contains("NZD")) {
            userExchange.setNzd(updateExchange.getCurrencyName());
        } else if (updateExchange.getCurrencyName().contains("HKD")) {
            userExchange.setHkd(updateExchange.getCurrencyName());
        } else if (updateExchange.getCurrencyName().contains("GBP")) {
            userExchange.setGbp(updateExchange.getCurrencyName());
        }

        userExchangeRepository.save(userExchange);
    }

    public List<UserAllExchangeDto> getExchange(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = myPageService.searchMember(userId);

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
        LazierUser lazierUser = myPageService.searchMember(userId);
        List<UserPartialExchangeDto> userPartialExchangeDtoList = new ArrayList<>();

        UserExchange userExchange = userExchangeRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        String[] checkList = {userExchange.getUsd(), userExchange.getJpy(), userExchange.getEur(),
            userExchange.getCny(), userExchange.getHkd(), userExchange.getGbp(),
            userExchange.getChf(), userExchange.getCad(), userExchange.getAud(),
            userExchange.getNzd()};

        for (int i = 0; i < 10; i++) {
            if (!checkList[i].contains("X")) {
                String currencyName = checkList[i];

                Exchange exchange = exchangeRepository.findByCurrencyNameOrderByUpdateAtDescCountryNameDesc(currencyName)
                    .orElseThrow(() -> new NotFoundExchangeException("선택한 환율 종목이 있는지 확인하세요."));

                UserPartialExchangeDto userPartialExchangeDto = UserPartialExchangeDto.builder()
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

                userPartialExchangeDtoList.add(userPartialExchangeDto);
            }
        }
        return userPartialExchangeDtoList;
    }
}
