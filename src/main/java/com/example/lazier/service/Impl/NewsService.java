package com.example.lazier.service.Impl;

import com.example.lazier.persist.entity.module.News;
import com.example.lazier.persist.entity.module.NewsPress;
import com.example.lazier.persist.repository.NewsPressRepository;
import com.example.lazier.persist.repository.NewsRepository;
import com.example.lazier.scraper.NewsScraper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

  private final NewsScraper newsScraper;
  private final NewsRepository newsRepository;
  private final NewsPressRepository newsPressRepository;

  //초기화.

  public void dbInit() {
    String year = Integer.toString(LocalDateTime.now().getYear());
    String month = Integer.toString(LocalDateTime.now().getMonthValue());
    if (month.length() < 2) {
      month = "0" + month;
    }
    String day = Integer.toString(LocalDateTime.now().getDayOfMonth());
    if (day.length() < 2) {
      day = "0" + day;
    }
    String todayString = year + month + day;
    newsScraper.crawlPressList().stream().map(NewsPress::new).forEach(entity ->
        {
          boolean exists = newsPressRepository.existsById(entity.getPressId());
          if (!exists) {
            newsPressRepository.save(entity);
            log.info(entity.getPressName() + "is saved");
          }
        }
    );

    List<String> pressIdList = newsPressRepository.findAll().stream().map(NewsPress::getPressId)
        .collect(Collectors.toList());

    pressIdList.stream()
        .forEach(id -> newsScraper.crawlNewsByPressAndDate(id, todayString).stream().map(
                News::new).forEach(entity -> {
              boolean exists = newsRepository.existsById(entity.getNewsId());
              if (!exists) {
                newsRepository.save(entity);
                log.info(entity.getSubject() + "is saved");
              }
            })
        );
  }


}
