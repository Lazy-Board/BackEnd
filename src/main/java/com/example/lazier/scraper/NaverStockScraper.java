package com.example.lazier.scraper;

import com.example.lazier.dto.module.StockDto;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NaverStockScraper {

    final String SAMSUNG_ELECTRONICS_URL = "https://finance.naver.com/item/main.naver?code=005930";
    final String SKHYNIX_URL = "https://finance.naver.com/item/main.naver?code=000660";
    final String NAVER_URL = "https://finance.naver.com/item/main.naver?code=035420";
    final String KAKAO_URL = "https://finance.naver.com/item/main.naver?code=035720";
    final String HYUNDAI_CAR_URL = "https://finance.naver.com/item/main.naver?code=005380";
    final String KIA_URL = "https://finance.naver.com/item/main.naver?code=000270";
    final String LG_ELECTRONICS_URL = "https://finance.naver.com/item/main.naver?code=066570";
    final String KAKAO_BANK_URL = "https://finance.naver.com/item/main.naver?code=323410";
    final String SAMSUNG_SDI_URL = "https://finance.naver.com/item/main.naver?code=006400";
    final String HIVE_URL = "https://finance.naver.com/item/main.naver?code=352820";

    public List<StockDto> NaverScrap() {

        String[] urlArr = {SAMSUNG_ELECTRONICS_URL, SKHYNIX_URL, NAVER_URL, KAKAO_URL,
            HYUNDAI_CAR_URL, KIA_URL, LG_ELECTRONICS_URL, KAKAO_BANK_URL,
            SAMSUNG_SDI_URL, HIVE_URL};

        List<StockDto> stockDtoList = new ArrayList<>();

        try {
            for (int i = 0; i < 10; i++) {
                Connection conn = Jsoup.connect(urlArr[i]);
                Document document = conn.get();

                Elements totalStockInfo = document.select(".new_totalinfo dl>dd");
                String[] stockInfo = totalStockInfo.text().split(" ");

                switch (stockInfo[15]) {
                    case "하락":
                        stockInfo[15] = "-";
                        break;
                    case "상승":
                        stockInfo[15] = "+";
                        break;
                    case "보합":
                        stockInfo[15] = stockInfo[17];
                        break;
                }

                String stockName = stockInfo[8];
                String price = stockInfo[13];                                                // 현재가
                StringBuilder dayRange = new StringBuilder(
                    totalStockInfo.get(3).text().split(" ")[6]);                       // 등락률

                if (Objects.equals(totalStockInfo.get(3).text().split(" ")[3], "보합")) {
                   dayRange = new StringBuilder(
                        totalStockInfo.get(3).text().split(" ")[5]);
                   dayRange.insert(4, "%");
                }

                StringBuilder diffAmount = new StringBuilder(
                    totalStockInfo.get(3).text().split(" ")[4]);                        // 전일비

                String marketPrice = totalStockInfo.get(5).text().split(" ")[1];        // 시가
                String highPrice = totalStockInfo.get(6).text().split(" ")[1];          // 고가
                String lowPrice = totalStockInfo.get(8).text().split(" ")[1];           // 저가
                String tradingVolume = totalStockInfo.get(10).text().split(" ")[1];     // 거래량

                if ((Objects.equals(stockInfo[15], "+"))) {
                    dayRange = new StringBuilder(stockInfo[15] + dayRange + "%");
                    diffAmount.insert(0, "▲ ");
                } else if ((Objects.equals(stockInfo[15], "-"))) {
                    dayRange = new StringBuilder(stockInfo[15] + dayRange + "%");
                    diffAmount.insert(0, "▼ ");
                }

                StockDto stockDto = StockDto.builder()
                                            .stockName(stockName)
                                            .price(price)
                                            .diffAmount(diffAmount.toString())
                                            .dayRange(dayRange.toString())
                                            .marketPrice(marketPrice)
                                            .highPrice(highPrice)
                                            .lowPrice(lowPrice)
                                            .tradingVolume(tradingVolume)
                                            .updateAt(updatedDateText(LocalDateTime.now()))
                                            .build();

                stockDtoList.add(stockDto);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stockDtoList;
    }

    public String updatedDateText(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return now != null ? now.format(formatter) : "";
    }
}
