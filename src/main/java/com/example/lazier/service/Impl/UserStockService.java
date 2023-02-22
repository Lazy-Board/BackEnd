package com.example.lazier.service.Impl;

import static com.example.lazier.type.StockName.LG전자;
import static com.example.lazier.type.StockName.NAVER;
import static com.example.lazier.type.StockName.SK하이닉스;
import static com.example.lazier.type.StockName.기아;
import static com.example.lazier.type.StockName.삼성SDI;
import static com.example.lazier.type.StockName.삼성전자;
import static com.example.lazier.type.StockName.카카오;
import static com.example.lazier.type.StockName.카카오뱅크;
import static com.example.lazier.type.StockName.하이브;
import static com.example.lazier.type.StockName.현대차;

import com.example.lazier.dto.module.UserAllStockDto;
import com.example.lazier.dto.module.UserPartialStockDto;
import com.example.lazier.dto.module.UserStockInput;
import com.example.lazier.exception.UserAlreadyExistException;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.Stock;
import com.example.lazier.persist.entity.module.UserStock;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.persist.repository.StockRepository;
import com.example.lazier.persist.repository.UserStockRepository;
import com.example.lazier.service.user.MemberService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserStockService {

    private final StockService stockService;

    private final UserStockRepository userStockRepository;

    private final StockRepository stockRepository;

    private final MemberService memberService;

    @Transactional
    public void add(HttpServletRequest request, UserStockInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        if (userStockRepository.existsById(parameter.getUserId())) {
            throw new UserAlreadyExistException("사용자 정보가 이미 존재합니다.");
        }

        UserStock userStock = UserStock.builder()
                            .lazierUser(lazierUser)
                            .samsungElectronic(parameter.getSamsungElectronic() + 삼성전자)
                            .skHynix(parameter.getSkHynix() + SK하이닉스)
                            .naver(parameter.getNaver() + NAVER)
                            .kakao(parameter.getKakao() + 카카오)
                            .hyundaiCar(parameter.getHyundaiCar() + 현대차)
                            .kia(parameter.getKia() + 기아)
                            .lgElectronic(parameter.getLgElectronic() + LG전자)
                            .kakaoBank(parameter.getKakaoBank() + 카카오뱅크)
                            .samsungSdi(parameter.getSamsungSdi() + 삼성SDI)
                            .hive(parameter.getHive() + 하이브)
                            .build();

        userStockRepository.save(userStock);
        stockService.add();
    }

    public void update(HttpServletRequest request, UserStockInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        UserStock userStock = userStockRepository.findByLazierUser(lazierUser)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userStock.setSamsungElectronic(parameter.getSamsungElectronic() + 삼성전자);
        userStock.setSkHynix(parameter.getSkHynix() + SK하이닉스);
        userStock.setNaver(parameter.getNaver() + NAVER);
        userStock.setKakao(parameter.getKakao() + 카카오);
        userStock.setHyundaiCar(parameter.getHyundaiCar() + 현대차);
        userStock.setKia(parameter.getKia() + 기아);
        userStock.setLgElectronic(parameter.getLgElectronic() + LG전자);
        userStock.setKakaoBank(parameter.getKakaoBank() + 카카오뱅크);
        userStock.setSamsungSdi(parameter.getSamsungSdi() + 삼성SDI);
        userStock.setHive(parameter.getHive() + 하이브);

        userStockRepository.save(userStock);
    }

    public List<UserAllStockDto> getUserWantedStock(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        List<UserAllStockDto> userAllStockDtoList = new ArrayList<>();

        UserStock userStock = userStockRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        String[] checkList = {userStock.getSamsungElectronic(), userStock.getSkHynix(),
                            userStock.getNaver(), userStock.getKakao(), userStock.getHyundaiCar(),
                            userStock.getKia(), userStock.getLgElectronic(),
                            userStock.getKakaoBank(), userStock.getSamsungSdi(),
                            userStock.getHive()};

        for (int i = 0; i < 10; i++) {
            if (checkList[i].charAt(0) == 'Y') {
                String stockName = checkList[i].substring(1);

                Stock stock = stockRepository.findByStockNameOrderByUpdateAtDesc(stockName)
                    .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

                UserAllStockDto userAllStockDto = UserAllStockDto.builder()
                                                            .stockName(stock.getStockName())
                                                            .price(stock.getPrice())
                                                            .diffAmount(stock.getDiffAmount())
                                                            .dayRange(stock.getDayRange())
                                                            .marketPrice(stock.getMarketPrice())
                                                            .highPrice(stock.getHighPrice())
                                                            .lowPrice(stock.getLowPrice())
                                                            .tradingVolume(stock.getTradingVolume())
                                                            .updateAt(stock.getUpdateAt())
                                                            .build();

                userAllStockDtoList.add(userAllStockDto);
            }
        }
        return userAllStockDtoList;
    }

    public List<UserPartialStockDto> getUserPartialStock(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);
        List<UserPartialStockDto> userPartialStockDtoList = new ArrayList<>();

        UserStock userStock = userStockRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        String[] checkList = {userStock.getSamsungElectronic(), userStock.getSkHynix(),
                            userStock.getNaver(), userStock.getKakao(), userStock.getHyundaiCar(),
                            userStock.getKia(), userStock.getLgElectronic(),
                            userStock.getKakaoBank(), userStock.getSamsungSdi(),
                            userStock.getHive()};

        for (int i = 0; i < 10; i++) {
            if (checkList[i].charAt(0) == 'Y') {
                String stockName = checkList[i].substring(1);

                Stock stock = stockRepository.findByStockNameOrderByUpdateAtDesc(stockName)
                    .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

                UserPartialStockDto userPartialStockDto = UserPartialStockDto.builder()
                                                                .stockName(stock.getStockName())
                                                                .price(stock.getPrice())
                                                                .diffAmount(stock.getDiffAmount())
                                                                .dayRange(stock.getDayRange())
                                                                .updateAt(stock.getUpdateAt())
                                                                .build();

                userPartialStockDtoList.add(userPartialStockDto);
            }
        }
        return userPartialStockDtoList;
    }
}
