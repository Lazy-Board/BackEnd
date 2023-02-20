package com.example.lazier.scraper;

import com.example.lazier.dto.module.NewsPressDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

  public List<NewsPressDto> pressListCrawl() {
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

      for (int i = 0; i < baseElements.size(); i++) {
        String sectorName = baseElements.get(i).select("th").text();
        Elements pressNameElements = baseElements.get(i).select("td ul li");
        NewsPressDto dto = new NewsPressDto(sectorName);
        for (int j = 0; j < pressNameElements.size(); j++) {
          Element pressInfo = pressNameElements.select("a").get(j);
          String pressIdUrl = pressInfo.attr("href");
          String pressId = pressIdUrl.substring(pressIdUrl.lastIndexOf('=') + 1);
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

}
