package com.example.lazier.scheduler;

import com.example.lazier.persist.entity.module.News;
import com.example.lazier.persist.entity.module.NewsPress;
import com.example.lazier.persist.repository.NewsPressRepository;
import com.example.lazier.persist.repository.NewsRepository;
import com.example.lazier.scraper.NewsScraper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@EnableCaching
@AllArgsConstructor
public class NewsScraperScheduler {

  private final NewsRepository newsRepository;
  private final NewsPressRepository newsPressRepository;
  private final NewsScraper newsScraper;

  //        @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
  @Scheduled(cron = "${scheduler.scrap.news}")
  @Transactional
  public void NewsScheduling() {
    log.info("youtube scraping scheduler has started");
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

    List<String> pressIdList = newsPressRepository.findAll().stream().map(NewsPress::getPressId)
        .collect(Collectors.toList());

    pressIdList
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

  @Scheduled(cron = "${scheduler.scrap.newspress}")
  @Transactional
  public void NewsPressScheduling() {
    log.info("NewsPressScheduling has started");
    newsScraper.crawlPressList().stream().map(NewsPress::new).forEach(entity ->
        {
          boolean exists = newsPressRepository.existsById(entity.getPressId());
          if (!exists) {
            newsPressRepository.save(entity);
          }
        }
    );
  }

  //업데이트 된지 2일 이상 된 영상은 삭제한다.
  @Scheduled(cron = "${scheduler.scrap.news}")
  @Transactional
  public void NewsDbUpdater() {
    log.info("NewsDb Deleting scheduler has started");

    List<News> newsEntites = this.newsRepository.findAllByUpdatedAtBefore(
        LocalDateTime.now().minusDays(2));
    newsRepository.deleteAll(newsEntites);
  }


}

