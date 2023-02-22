package com.example.lazier.scraper;

import com.example.lazier.dto.module.NewsDto;
import com.example.lazier.dto.module.NewsPressDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class NewsScraper {

  String html = null;
  HttpURLConnection con;
  final String NEWS_PRESS_LIST_URL = "https://news.naver.com/main/officeList.naver";
  final String NEWS_URL_BY_PRESS_AND_DATE = "https://news.naver.com/main/list.naver?mode=LPOD&mid=sec&oid=%s&listType=summary&date=%s";

  public List<NewsPressDto> crawlPressList() {
    List<NewsPressDto> newsPressDtos = new ArrayList<>();
    try {
      URL url = new URL(NEWS_PRESS_LIST_URL);
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
      Elements baseElements = document.getElementsByAttributeValue("class", "group_table")
          .select("tbody tr");
      System.out.println(baseElements.size());

      for (int i = 0; i < baseElements.size(); i++) {
        String sectorName = baseElements.get(i).select("th").text();
        Elements pressNameElements = baseElements.get(i).select("td ul li");
        System.out.println(pressNameElements);
        System.out.println(pressNameElements.size());

        for (int j = 0; j < pressNameElements.size(); j++) {
        NewsPressDto dto = new NewsPressDto(sectorName);
          System.out.println("j = " + j);
          Element pressInfo = pressNameElements.select("a").get(j);
          String pressIdUrl = pressInfo.attr("href");
          int index = pressIdUrl.indexOf("oid=")+4;
          String pressId = pressIdUrl.substring(index,index+3);
          String pressName = pressInfo.text();

          dto.setPressName(pressInfo.text());
          dto.setPressId(pressId);
          dto.setPressName(pressName);
          newsPressDtos.add(dto);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return newsPressDtos;
  }


  //String date : 8자리 날짜 입력. ex 20230220
  public List<NewsDto> crawlNewsByPressAndDate(String pressId, String date) {
    List<NewsDto> newsDtoList = new ArrayList<>();

    try {
      String stringUrl = String.format(NEWS_URL_BY_PRESS_AND_DATE, pressId, date);
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
      Elements pageElements = document.getElementsByAttributeValue("class", "nclicks(fls.page)");
      int maxPageNum = pageElements.size() + 1;
      //페이지 별로 돌면서 뉴스 넣기
      for (int i = 1; i <= maxPageNum; i++) {
        String stringUrlByPage = stringUrl + "&page=" + i;
        url = new URL(stringUrlByPage);
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
        pageElements = document.getElementsByAttributeValue("class", "list_body newsflash_body");

        //페이지별 뉴스 리스트
        Elements newsElements = pageElements.select("li");

        ///각 li태그 내에 데이터 존재
        for (int j = 0; j < newsElements.size(); j++) {
          Element newsElement = newsElements.get(j);

          String subject = newsElement.select("dl dt a").text();
          String newsUrl = newsElement.select("dl dt a").attr("href")
              .toString();
          int index = newsUrl.lastIndexOf('/');
          String newsId = newsUrl.substring(index + 1);
          String contents = newsElement.select("span.lede").text();
          String createdAt = newsElement.select("span.date").text();
          String imagePath = newsElement.select("img").attr("src");

          newsDtoList.add(NewsDto.builder()
              .newsId(newsId)
              .subject(subject)
              .pressId(pressId)
              .createdAt(createdAt)
              .contents(contents)
              .url(newsUrl)
              .imagePath(imagePath)
              .updatedAt(LocalDateTime.now())
              .build()
          );
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return newsDtoList;
  }

}
