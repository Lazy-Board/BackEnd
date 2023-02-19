package com.example.lazier.stockModule.service.Impl;

import static com.example.lazier.stockModule.type.StockName.LG전자;
import static com.example.lazier.stockModule.type.StockName.NAVER;
import static com.example.lazier.stockModule.type.StockName.SK하이닉스;
import static com.example.lazier.stockModule.type.StockName.기아;
import static com.example.lazier.stockModule.type.StockName.삼성SDI;
import static com.example.lazier.stockModule.type.StockName.삼성전자;
import static com.example.lazier.stockModule.type.StockName.카카오;
import static com.example.lazier.stockModule.type.StockName.카카오뱅크;
import static com.example.lazier.stockModule.type.StockName.하이브;
import static com.example.lazier.stockModule.type.StockName.현대차;

import com.example.lazier.stockModule.dto.UserAllStockDto;
import com.example.lazier.stockModule.dto.UserPartialStockDto;
import com.example.lazier.stockModule.dto.UserStockInput;
import com.example.lazier.stockModule.exception.NotFoundStockException;
import com.example.lazier.stockModule.persist.entity.Stock;
import com.example.lazier.stockModule.persist.entity.UserStock;
import com.example.lazier.stockModule.persist.repository.StockRepository;
import com.example.lazier.stockModule.persist.repository.UserStockRepository;
import com.example.lazier.stockModule.service.StockService;
import com.example.lazier.stockModule.service.UserStockService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserStockServiceImpl implements UserStockService {

    private final StockService stockService;

    private final UserStockRepository userStockRepository;

    private final StockRepository stockRepository;

    @Override
    @Transactional
    public void add(UserStockInput parameter) {
        UserStock userStock = UserStock.builder()
                            .userId(parameter.getUserId())
                            .samsungElectronic("Y" + 삼성전자)
                            .skHynix("Y" + SK하이닉스)
                            .naver("Y" + NAVER)
                            .kakao("Y" + 카카오)
                            .hyundaiCar("N" + 현대차)
                            .kia("N" + 기아)
                            .lgElectronic("N" + LG전자)
                            .kakaoBank("N" + 카카오뱅크)
                            .samsungSdi("N" + 삼성SDI)
                            .hive("N" + 하이브)
                            .build();
        userStockRepository.save(userStock);
        stockService.add();
    }

    @Override
    public void update(HttpServletRequest request, UserStockInput parameter) {
        String userId = (String) request.getAttribute("userId");
        parameter.setUserId(userId);

        Optional<UserStock> optionalUserStock = userStockRepository.findById(userId);
        if (!optionalUserStock.isPresent()) {
            throw new NotFoundStockException("정보가 존재하지 않습니다.");
        }

        UserStock userStock = optionalUserStock.get();

        userStock.setSamsungElectronic("N" + 삼성전자);
        userStock.setSkHynix("Y" + SK하이닉스);
        userStock.setNaver("Y" + NAVER);
        userStock.setKakao("N" + 카카오);
        userStock.setHyundaiCar("Y" + 현대차);
        userStock.setKia("Y" + 기아);
        userStock.setLgElectronic("Y" + LG전자);
        userStock.setKakaoBank("N" + 카카오뱅크);
        userStock.setSamsungSdi("Y" + 삼성SDI);
        userStock.setHive("Y" + 하이브);

        userStockRepository.save(userStock);
    }

    @Override
    public List<UserAllStockDto> getUserWantedStock(String userId) {
        List<UserAllStockDto> userAllStockDtoList = new ArrayList<>();
        Optional<UserStock> optionalUserStock = userStockRepository.findById(userId);

        if (!optionalUserStock.isPresent()) {
            throw new NotFoundStockException("정보가 존재하지 않습니다.");
        }

        UserStock userStock = optionalUserStock.get();

        String[] checkList = {userStock.getSamsungElectronic(), userStock.getSkHynix(),
                            userStock.getNaver(), userStock.getKakao(), userStock.getHyundaiCar(),
                            userStock.getKia(), userStock.getLgElectronic(),
                            userStock.getKakaoBank(), userStock.getSamsungSdi(),
                            userStock.getHive()};

        for (int i = 0; i < 10; i++) {
            if (checkList[i].charAt(0) == 'Y') {
                String stockName = checkList[i].substring(1);
                Optional<Stock> optionalStock =
                    stockRepository.findByStockNameOrderByUpdateAtDesc(stockName);

                if (!optionalStock.isPresent()) {
                    throw new NotFoundStockException("정보가 존재하지 않습니다.");
                }

                Stock stock = optionalStock.get();

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

    @Override
    public List<UserPartialStockDto> getUserPartialStock(String userId) {
        List<UserPartialStockDto> userPartialStockDtoList = new ArrayList<>();
        Optional<UserStock> optionalUserStock = userStockRepository.findById(userId);

        if (!optionalUserStock.isPresent()) {
            throw new NotFoundStockException("정보가 존재하지 않습니다.");
        }

        UserStock userStock = optionalUserStock.get();

        String[] checkList = {userStock.getSamsungElectronic(), userStock.getSkHynix(),
                            userStock.getNaver(), userStock.getKakao(), userStock.getHyundaiCar(),
                            userStock.getKia(), userStock.getLgElectronic(),
                            userStock.getKakaoBank(), userStock.getSamsungSdi(),
                            userStock.getHive()};

        for (int i = 0; i < 10; i++) {
            if (checkList[i].charAt(0) == 'Y') {
                String stockName = checkList[i].substring(1);

                Optional<Stock> optionalStock =
                    stockRepository.findByStockNameOrderByUpdateAtDesc(stockName);

                if (!optionalStock.isPresent()) {
                    throw new NotFoundStockException("정보가 존재하지 않습니다.");
                }

                Stock stock = optionalStock.get();

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
