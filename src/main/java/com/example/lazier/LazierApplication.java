package com.example.lazier;


import com.example.lazier.dto.module.NewsDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@SpringBootApplication
public class LazierApplication {

  public static void main(String[] args) {
//    SpringApplication.run(LazierApplication.class, args);

    String html = null;
    HttpURLConnection con;
    final String NEWS_PRESS_LIST_URL = "https://news.naver.com/main/officeList.naver";
    final String NEWS_URL_BY_PRESS_AND_DATE = "https://news.naver.com/main/list.naver?mode=LPOD&mid=sec&oid=%s&listType=summary&date=%s";
//    final String NEWS_URL_BY_PRESS_AND_DATE_And_Page =  "https://news.naver.com/main/list.naver?mode=LPOD&mid=sec&oid=%s&listType=summary&date=%s&page=d";
////////////////////////////////////////////////////////////

    List<NewsDto> newsDtos = new ArrayList<>();
    String date = "20230221";
    String paramId = "032";

    try {
      String stringUrl = String.format(NEWS_URL_BY_PRESS_AND_DATE, paramId, date);
      URL url = new URL(stringUrl);
      con = (HttpURLConnection) url.openConnection();
      con.setUseCaches(true);
      con.setConnectTimeout(5000);
      BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "EUC-kr"));
      StringBuilder sb = new StringBuilder();
      while (true) {
        String line = br.readLine();
        if (line == null) {
          break;
        }
        sb.append(line + "\n");
      }
      html = sb.toString();
      br.close();
      con.disconnect();
      Document document = Jsoup.parse(html);
      Elements pageElement = document.getElementsByAttributeValue("class", "nclicks(fls.page)");
      int maxPageNum = pageElement.size() + 1;

      //페이지 별로 돌면서 뉴스 넣기
      for (int i = 1; i <= pageElement.size(); i++) {
        String stringUrlByPage = stringUrl + "page=" + i;
        url = new URL(stringUrl);
        con = (HttpURLConnection) url.openConnection();
        con.setUseCaches(true);
        con.setConnectTimeout(5000);
        br = new BufferedReader(new InputStreamReader(con.getInputStream(), "EUC-kr"));
        sb = new StringBuilder();
        while (true) {
          String line = br.readLine();
          if (line == null) {
            break;
          }
          sb.append(line + "\n");
        }
        html = sb.toString();
        br.close();
        con.disconnect();
        document = Jsoup.parse(html);
        pageElement = document.getElementsByAttributeValue("class", "nclicks(fls.page)");

      }

//      for (int i = 0; i < baseElements.size(); i++) {
//        String sectorName = baseElements.get(i).select("th").text();
//        Elements pressNameElements = baseElements.get(i).select("td ul li");
//        NewsPressDto dto = new NewsPressDto(sectorName);
//        for (int j = 0; j < pressNameElements.size(); j++) {
//          Element pressInfo = pressNameElements.select("a").get(j);
//          String pressIdUrl = pressInfo.attr("href");
//          String pressId = pressIdUrl.substring(pressIdUrl.lastIndexOf('=') + 1);
//          String pressName = pressInfo.text();
//
//          dto.setPressName(pressInfo.text());
//          dto.setPressId(pressId);
//          dto.setPressName(pressName);
//          newsPressDtos.add(dto);
//        }
//      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }


  }

}