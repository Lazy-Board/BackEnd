package com.example.lazier.YoutubeModule.scheduler;

import com.example.lazier.YoutubeModule.model.ScrapedResult;
import com.example.lazier.YoutubeModule.persist.entity.YoutubeEntity;
import com.example.lazier.YoutubeModule.persist.repository.YoutubeRepository;
import com.example.lazier.YoutubeModule.scrapper.YoutubeScraper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableCaching
@AllArgsConstructor
public class ScraperScheduler {

    private final YoutubeRepository youtubeRepository;
    private final YoutubeScraper youtubeScraper;

    //    @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
    @Scheduled(cron = "${scheduler.scrap.youtube}")
    public void youtubeScheduling() {
        log.info("youtube scraping scheduler has started");

        // 업데이트 할 크롤링 내용
        ScrapedResult scrapedResult = this.youtubeScraper.crawl();

        // 스크래핑한 정보 중 없는 값을 저장
        scrapedResult.getYoutubeList().stream()
                .map(e -> new YoutubeEntity(e.getVideoId()).builder()
                        .contentName(e.getContentName())
                        .createdAt(e.getCreatedAt())
                        .channelName(e.getChannelName())
                        .updatedAt(e.getUpdatedAt())
                        .length(e.getLength())
                        .imagePath(e.getImagePath())
                        .hit(e.getNumViewers())
                        .build()
                )

                .forEach(e ->
                {
                    boolean exists = this.youtubeRepository.existsById(e.getVideoId());
                    if (!exists) {
                        this.youtubeRepository.save(e);
                        System.out.println(e.getContentName() + " is saved");
                    }
                });
        //스크래핑 연속요청이 되지 않도록 일시정지
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
    }
}

