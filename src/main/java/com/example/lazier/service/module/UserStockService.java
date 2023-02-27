package com.example.lazier.service.module;

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
import com.example.lazier.exception.NotFoundStockException;
import com.example.lazier.exception.UserAlreadyExistException;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.DetailStock;
import com.example.lazier.persist.entity.module.Stock;
import com.example.lazier.persist.entity.module.UserStock;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.persist.repository.DetailStockRepository;
import com.example.lazier.persist.repository.StockRepository;
import com.example.lazier.persist.repository.UserStockRepository;
import com.example.lazier.service.user.MemberService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserStockService {

    private final StockService stockService;

    private final UserStockRepository userStockRepository;

    private final StockRepository stockRepository;

    private final DetailStockRepository detailStockRepository;

    private final MemberService memberService;

    @Transactional
    public void add(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        if (userStockRepository.existsByLazierUser(lazierUser)) {
            throw new UserAlreadyExistException("사용자 정보가 이미 존재합니다.");
        }

        UserStock userStock = UserStock.builder()
                                        .lazierUser(lazierUser)
                                        .samsungElectronic(String.valueOf(삼성전자))
                                        .skHynix(String.valueOf(SK하이닉스))
                                        .naver(String.valueOf(NAVER))
                                        .kakao(String.valueOf(카카오))
                                        .hyundaiCar("N")
                                        .kia("N")
                                        .lgElectronic("N")
                                        .kakaoBank("N")
                                        .samsungSdi("N")
                                        .hive("N")
                                        .build();

        DetailStock detailStock = DetailStock.builder()
                                            .lazierUser(lazierUser)
                                            .samsungElectronic(String.valueOf(삼성전자))
                                            .skHynix(String.valueOf(SK하이닉스))
                                            .naver(String.valueOf(NAVER))
                                            .kakao(String.valueOf(카카오))
                                            .hyundaiCar(String.valueOf(현대차))
                                            .kia(String.valueOf(기아))
                                            .lgElectronic(String.valueOf(LG전자))
                                            .kakaoBank(String.valueOf(카카오뱅크))
                                            .samsungSdi(String.valueOf(삼성SDI))
                                            .hive(String.valueOf(하이브))
                                            .build();

        userStockRepository.save(userStock);
        detailStockRepository.save(detailStock);
        stockService.add();
    }

    public void update(HttpServletRequest request, UserStockInput parameter) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        UserStock userStock = userStockRepository.findByLazierUser(lazierUser)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userStock.setSamsungElectronic(parameter.getSamsungElectronic());
        userStock.setSkHynix(parameter.getSkHynix());
        userStock.setNaver(parameter.getNaver());
        userStock.setKakao(parameter.getKakao());
        userStock.setHyundaiCar(parameter.getHyundaiCar());
        userStock.setKia(parameter.getKia());
        userStock.setLgElectronic(parameter.getLgElectronic());
        userStock.setKakaoBank(parameter.getKakaoBank());
        userStock.setSamsungSdi(parameter.getSamsungSdi());
        userStock.setHive(parameter.getHive());

        userStockRepository.save(userStock);
    }

    public List<UserAllStockDto> getStock(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());
        LazierUser lazierUser = memberService.searchMember(userId);

        List<UserAllStockDto> userAllStockDtoList = new ArrayList<>();

        DetailStock detailStock = detailStockRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("선택한 주식 종목이 있는지 확인하세요."));

        String[] checkList = {detailStock.getSamsungElectronic(), detailStock.getSkHynix(),
                        detailStock.getNaver(), detailStock.getKakao(), detailStock.getHyundaiCar(),
                        detailStock.getKia(), detailStock.getLgElectronic(),
                        detailStock.getKakaoBank(), detailStock.getSamsungSdi(),
                        detailStock.getHive()};

        for (int i = 0; i < 10; i++) {
            String stockName = checkList[i];
            Stock stock = stockRepository.findByStockNameOrderByUpdateAtDesc(stockName)
                .orElseThrow(() -> new NotFoundStockException("선택한 주식 종목이 있는지 확인하세요."));

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
        return userAllStockDtoList;
    }

    public List<UserPartialStockDto> getPartialStock(HttpServletRequest request) {
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
            if (checkList[i].length() > 1) {
                String stockName = checkList[i];

                Stock stock = stockRepository.findByStockNameOrderByUpdateAtDesc(stockName)
                    .orElseThrow(() -> new NotFoundStockException("선택한 주식 종목이 있는지 확인하세요."));

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
