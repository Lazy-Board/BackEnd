package com.example.lazier;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@SpringBootApplication
public class LazierApplication {

  public static void main(String[] args) {
    SpringApplication.run(LazierApplication.class, args);
//
//    String html = null;
//    HttpURLConnection con;
//    final String NEWS_PRESS_LIST_URL = "https://news.naver.com/main/officeList.naver";
//
//    List<NewsPressDto> newsPressDtos = new ArrayList<>();
//    try {
//      URL url = new URL(NEWS_PRESS_LIST_URL);
//      con = (HttpURLConnection) url.openConnection();
//      con.setUseCaches(true);
//      con.setConnectTimeout(5000);
//      BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "EUC-kr"));
//      StringBuilder sb = new StringBuilder();
//      while (true) {
//        String line = br.readLine();
//        if (line == null) {
//          break;
//        }
//        sb.append(line + "\n");
//      }
//      html = sb.toString();
//      br.close();
//      con.disconnect();
//      String str;
//      String str2 = null;
//      Document document = Jsoup.parse(html);
////      System.out.println(html);
//      Elements baseElements = document.getElementsByAttributeValue("class", "group_table")
//          .select("tbody tr");
//
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
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }

  }

}