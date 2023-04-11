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

import com.example.lazier.dto.module.UpdateStockDto;
import com.example.lazier.dto.module.UserAllStockDto;
import com.example.lazier.dto.module.UserPartialStockDto;
import com.example.lazier.exception.NotFoundStockException;
import com.example.lazier.exception.UserNotFoundException;
import com.example.lazier.persist.entity.module.DetailStock;
import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.persist.entity.module.Stock;
import com.example.lazier.persist.entity.module.UpdateStock;
import com.example.lazier.persist.entity.module.UserStock;
import com.example.lazier.persist.repository.DetailStockRepository;
import com.example.lazier.persist.repository.MemberRepository;
import com.example.lazier.persist.repository.StockRepository;
import com.example.lazier.persist.repository.UpdateStockRepository;
import com.example.lazier.persist.repository.UserStockRepository;
import java.util.ArrayList;
import java.util.List;
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

    private final UpdateStockRepository updateStockRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void add(String paramId) {
        long userId = Long.parseLong(paramId);
        LazierUser lazierUser = memberRepository.findByUserId(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));


        if (!userStockRepository.existsByLazierUser(lazierUser)) {
            UserStock userStock = UserStock.builder()
                                        .lazierUser(lazierUser)
                                        .samsungElectronic(String.valueOf(삼성전자))
                                        .skHynix(String.valueOf(SK하이닉스))
                                        .naver(String.valueOf(NAVER))
                                        .kakao("F")
                                        .hyundaiCar("F")
                                        .kia("F")
                                        .lgElectronic("F")
                                        .kakaoBank("F")
                                        .samsungSdi("F")
                                        .hive("F")
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
//            stockService.add();
        }
    }


    public void update(HttpServletRequest request, UpdateStockDto updateStockDto) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());

        LazierUser lazierUser = memberRepository.findByUserId(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        if (updateStockRepository.existsByLazierUser(lazierUser)) {
            UpdateStock updateStock = updateStockRepository.findByLazierUser(lazierUser)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

            updateStock.setLazierUser(lazierUser);
            updateStock.setStockName(updateStockDto.getStockName());

            if (updateStock.getStockName().contains("삼성전자")) {
                updateStock.setSamsungElectronic(updateStock.getStockName());
            } else if (updateStock.getStockName().contains("SK하이닉스")) {
                updateStock.setSkHynix(updateStock.getStockName());
            } else if (updateStock.getStockName().contains("NAVER")) {
                updateStock.setNaver(updateStock.getStockName());
            } else if (updateStock.getStockName().contains("삼성SDI")) {
                updateStock.setSamsungSdi(updateStock.getStockName());
            } else if (updateStock.getStockName().contains("LG전자")) {
                updateStock.setLgElectronic(updateStock.getStockName());
            } else if (updateStock.getStockName().contains("카카오뱅크")) {
                updateStock.setKakaoBank(updateStock.getStockName());
            } else if (updateStock.getStockName().contains("카카오")) {
                updateStock.setKakao(updateStock.getStockName());
            } else if (updateStock.getStockName().contains("하이브")) {
                updateStock.setHive(updateStock.getStockName());
            } else if (updateStock.getStockName().contains("현대차")) {
                updateStock.setHyundaiCar(updateStock.getStockName());
            } else if (updateStock.getStockName().contains("기아")) {
                updateStock.setKia(updateStock.getStockName());
            }

            updateStockRepository.save(updateStock);
        } else {
            UpdateStock updateStock = UpdateStock.builder()
                .lazierUser(lazierUser)
                .stockName(updateStockDto.getStockName())
                .build();
            updateStockRepository.save(updateStock);
        }

        UpdateStock updateStock = updateStockRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        UserStock userStock = userStockRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        if (updateStock.getStockName().contains("삼성전자")) {
            userStock.setSamsungElectronic(updateStock.getStockName());
        } else if (updateStock.getStockName().contains("SK하이닉스")) {
            userStock.setSkHynix(updateStock.getStockName());
        } else if (updateStock.getStockName().contains("NAVER")) {
            userStock.setNaver(updateStock.getStockName());
        } else if (updateStock.getStockName().contains("삼성SDI")) {
            userStock.setSamsungSdi(updateStock.getStockName());
        } else if (updateStock.getStockName().contains("LG전자")) {
            userStock.setLgElectronic(updateStock.getStockName());
        } else if (updateStock.getStockName().contains("카카오뱅크")) {
            userStock.setKakaoBank(updateStock.getStockName());
        } else if (updateStock.getStockName().contains("카카오")) {
            userStock.setKakao(updateStock.getStockName());
        } else if (updateStock.getStockName().contains("하이브")) {
            userStock.setHive(updateStock.getStockName());
        } else if (updateStock.getStockName().contains("현대차")) {
            userStock.setHyundaiCar(updateStock.getStockName());
        } else if (updateStock.getStockName().contains("기아")) {
            userStock.setKia(updateStock.getStockName());
        }

        userStockRepository.save(userStock);
    }

    public List<UserAllStockDto> getStock(HttpServletRequest request) {
        long userId = Long.parseLong(request.getAttribute("userId").toString());

        LazierUser lazierUser = memberRepository.findByUserId(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

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

        LazierUser lazierUser = memberRepository.findByUserId(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        List<UserPartialStockDto> userPartialStockDtoList = new ArrayList<>();

        UserStock userStock = userStockRepository.findByLazierUser(lazierUser)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        String[] checkList = {userStock.getSamsungElectronic(), userStock.getSkHynix(),
                            userStock.getNaver(), userStock.getKakao(), userStock.getHyundaiCar(),
                            userStock.getKia(), userStock.getLgElectronic(),
                            userStock.getKakaoBank(), userStock.getSamsungSdi(),
                            userStock.getHive()};

        for (int i = 0; i < 10; i++) {
            if (!checkList[i].contains("F")) {
                String stockName = checkList[i];
                Stock stock = stockRepository.findByStockNameOrderByUpdateAtDesc(stockName)
                    .orElseThrow(() -> new NotFoundStockException("선택한 주식 종목이 있는지 확인하세요."));

                UserPartialStockDto userPartialStockDto = UserPartialStockDto.builder()
                                                                .stockName(stock.getStockName())
                                                                .price(stock.getPrice())
                                                                .diffAmount(stock.getDiffAmount())
                                                                .dayRange(stock.getDayRange())
                                                                .updateAt(stock.getUpdateAt())
                                                                .marketPrice(stock.getMarketPrice())
                                                                .highPrice(stock.getHighPrice())
                                                                .lowPrice(stock.getLowPrice())
                                                                .tradingVolume(stock.getTradingVolume())
                                                                .updateAt(stock.getUpdateAt())
                                                                .build();

                userPartialStockDtoList.add(userPartialStockDto);
            }
        }
        return userPartialStockDtoList;
    }
}
