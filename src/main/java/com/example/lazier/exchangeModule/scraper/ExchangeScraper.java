package com.example.lazier.exchangeModule.scraper;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class ExchangeScraper implements Scraper {

    @Override
    public String scrap() {

        // 한국무역협회 실시간 환율정보에서 데이터 받아오기
        StringBuffer response = new StringBuffer();

        try {
            String connUrl = "https://www.kita.net/cmmrcInfo/ehgtGnrlzInfo/rltmEhgt.do";
            Document document = Jsoup.connect(connUrl).get();
            for (int i = 0; i < 10; i++) {
                Elements elem = document.select(
                    "#contents > div.boardArea > div.tableSt.st4.alc > table > tbody > "
                        + "tr:nth-child(" + (i + 1) + ")");

                Elements elem2 = document.select(
                    "#contents > div.boardArea > div.titArea2 > div.exInfo");

                String[] str = elem.text().split("\n");
                String[] exInfo = str[0].split(" ");

                String[] str2 = elem2.text().split("\n");
                String[] exInfo2 = str2[0].split(" ");

                String currencyName = exInfo[0] + " " + exInfo[1];                  // 통화명
                String tradingStandardRate = exInfo[2];                             // 등락율
                String comparedToThePreviousDay = exInfo[3] + " " + exInfo[4];      // 전일대비
                String fluctuationRate = exInfo[5];                                 // 등락율
                String buyCash = exInfo[6];                                         // 현재 살 때
                String sellCash = exInfo[7];
                String sendMoney = exInfo[8];
                String receiveMoney = exInfo[9];

                String updateAt = exInfo2[0];
                String round = "KB국민은행 " + exInfo2[1];

                Thread.sleep(200);

                response.append(currencyName + " " + tradingStandardRate + " "
                    + comparedToThePreviousDay + " " + fluctuationRate + " " + buyCash +
                    " " + sellCash + " " + sendMoney + " " + receiveMoney +
                    " " + updateAt +  " " + round + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.toString();
    }

}
