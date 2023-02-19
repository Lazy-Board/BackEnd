package com.example.lazier.service.Impl;

import com.example.lazier.dto.module.ScrapedResult;
import com.example.lazier.dto.module.YoutubeDto;
import com.example.lazier.persist.entity.module.Youtube;
import com.example.lazier.persist.repository.YoutubeRepository;
import com.example.lazier.scraper.YoutubeScraper;
import com.example.lazier.service.YoutubeService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class YoutubeServiceImpl implements YoutubeService {

  private final YoutubeScraper youtubeScraper;
  private final YoutubeRepository youtubeRepository;


  @Override
  @Transactional(readOnly = true)
  public List<YoutubeDto> getYoutube() {
    List<Youtube> youtubeEntities = youtubeRepository.findTop3ByOrderByCreatedAtDesc();
    List<YoutubeDto> youtubeDtoList = new ArrayList<>();
    for (Youtube entity : youtubeEntities) {
      youtubeDtoList.add(new YoutubeDto().from(entity));
    }
    return youtubeDtoList;
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
    scrapedResult.getYoutubeDtoList().stream().map(youtubeDto -> Youtube.builder()
        .videoId(youtubeDto.getVideoId())
        .contentName(youtubeDto.getContentName())
        .createdAt(youtubeDto.getCreatedAt())
        .channelName(youtubeDto.getChannelName())
        .updatedAt(youtubeDto.getUpdatedAt())
        .length(youtubeDto.getLength())
        .videoUrl(youtubeDto.getVideoUrl())
        .imagePath(youtubeDto.getImagePath())
        .hit(youtubeDto.getHit())
        .updatedAt(LocalDateTime.now())
        .build()
    ).forEach(entity ->
    {
      boolean exists = this.youtubeRepository.existsById(entity.getVideoId());
      if (!exists) {
        this.youtubeRepository.save(entity);
        log.info(entity.getContentName() + " is saved");
        log.info(entity.getVideoUrl());
      }
    });
  }
}
