package com.example.lazier.stockModule.scraper;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class NaverKosdacScraper implements KospiScraper {

    final String kosdacUrl = "https://finance.naver.com/sise/sise_market_sum.nhn?sosok=0";
    Connection conn = Jsoup.connect(kosdacUrl);

    @Override
    public String kospiScrap() {

        StringBuffer response = new StringBuffer();

        // 삼성전자 62,900 ▲ 100 +0.16% 100 3,754,993 5,969,783 50.59 10,679,961 10.20 13.92
        try {
            Document document = conn.get();
            String tbody = getStockList(document);   // 데이터 리스트

            for (int i = 0; i < 10; i++) {
                String[] tbodySplit = tbody.split("\n");
                String[] stockInfo = tbodySplit[i].split(" ");

                String stockName = stockInfo[1];				// 종목명
                String price = stockInfo[2];					// 현재가
                String diffAmount = stockInfo[3];				// 전일비
                String dayRange = stockInfo[4];					// 등락률
                String parValue = stockInfo[5];					// 액면가
                String marketCap = stockInfo[6];				// 시가총액
                String numberOfListedShares = stockInfo[7];		// 상장주식수
                String foreignOwnRate = stockInfo[8];			// 외국인비율
                String turnover = stockInfo[9];					// 거래량
                String per = stockInfo[10];						// PER (주가수익률)
                String roe = stockInfo[11];						// ROE (자기자본이익률)

                if (dayRange.contains("+")) {
                    diffAmount = "▲ " + diffAmount;
                } else {
                    diffAmount = "▼ " + diffAmount;
                }

                response.append(stockName + " " + price + " " + diffAmount + " "
                    + dayRange + " " + parValue + " " + marketCap + " " + numberOfListedShares + " "
                    + foreignOwnRate + " " + turnover + " " + per + " " + roe);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

    public String getStockList(Document document) {
        Elements stockTableBody = document.select("table.type_2 tbody tr");
        StringBuilder sb = new StringBuilder();
        for (Element element : stockTableBody) {
            if (element.attr("onmouseover").isEmpty()) {
                continue;
            }

            for (Element td : element.select("td")) {
                String text;

                if (td.select(".center a").attr("href").isEmpty()) {
                    text = td.text();
                } else {
                    text = "https://finance.naver.com" +
                        td.select(".center a").attr("href");
                }
                sb.append(text);
                sb.append(" ");
            }
            sb.append(System.getProperty("line.separator")); //줄바꿈
        }
        return sb.toString();
    }

}
