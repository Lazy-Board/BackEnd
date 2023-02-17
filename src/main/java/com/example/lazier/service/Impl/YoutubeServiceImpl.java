package com.example.lazier.service.Impl;

import com.example.lazier.dto.module.ScrapedResult;
import com.example.lazier.persist.entity.module.Youtube;
import com.example.lazier.persist.repository.YoutubeRepository;
import com.example.lazier.scraper.YoutubeScraper;
import com.example.lazier.service.YoutubeService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class YoutubeServiceImpl implements YoutubeService {

  private final YoutubeScraper youtubeScraper;
  private final YoutubeRepository youtubeRepository;


  @Override
  @Transactional(readOnly = true)
  public List<com.example.lazier.dto.module.Youtube> getYoutube() {
    List<Youtube> youtubeEntities = youtubeRepository.findTop3ByOrderByCreatedAtDesc();
    List<com.example.lazier.dto.module.Youtube> youtubeList = new ArrayList<>();
    for (Youtube entity : youtubeEntities) {
      youtubeList.add(new com.example.lazier.dto.module.Youtube().from(entity));
    }
    return youtubeList;
  }

  @Transactional
  public void updateYoutubeDbTest() {
    List<Youtube> youtubeEntities =
        this.youtubeRepository.findAllByCreatedAtBefore(LocalDateTime.now().minusDays(7));

    youtubeRepository.deleteAll(youtubeEntities);
  }

  @Transactional
  public void youtubeinit() {

    // 업데이트 할 크롤링 내용
    ScrapedResult scrapedResult = this.youtubeScraper.crawl();

    // 스크래핑한 정보 중 없는 값을 저장
    scrapedResult.getYoutubeList().stream().map(e ->
        new Youtube().builder()
            .videoId(e.getVideoId())
            .contentName(e.getContentName())
            .createdAt(e.getCreatedAt())
            .channelName(e.getChannelName())
            .updatedAt(e.getUpdatedAt())
            .length(e.getLength())
            .imagePath(e.getImagePath())
            .hit(e.getHit())
            .updatedAt(LocalDateTime.now())
            .build()
    ).forEach(entity ->
    {
      boolean exists = this.youtubeRepository.existsById(entity.getVideoId());
      if (!exists) {
        this.youtubeRepository.save(entity);
        System.out.println(entity.getContentName() + " is saved");
      }
    });
  }
}
