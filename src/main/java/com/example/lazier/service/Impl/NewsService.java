package com.example.lazier.service.Impl;

import com.example.lazier.persist.repository.NewsPressRepository;
import com.example.lazier.persist.repository.NewsRepository;
import com.example.lazier.scraper.NewsScraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService  {

  private final NewsScraper newsScraper;
  private final NewsRepository newsRepository;
  private final NewsPressRepository newsPressRepository;


//
//  public List<YoutubeDto> showNews(String userId) {
//    List<Youtube> youtubeEntities = youtubeRepository.findTop3ByOrderByCreatedAtDesc();
//    List<YoutubeDto> youtubeDtoList = new ArrayList<>();
//    for (Youtube entity : youtubeEntities) {
//      youtubeDtoList.add(new YoutubeDto().from(entity));
//    }
//    return youtubeDtoList;
//  }
//
//  @Transactional
//  public void updateYoutubeDbTest() {
//    List<Youtube> youtubeEntities =
//        this.newsRepository.findAllByCreatedAtBefore(LocalDateTime.now().minusDays(7));
//
//    youtubeRepository.deleteAll(youtubeEntities);
//  }
////
////  public void newsinit() {
////
////    // 업데이트 할 크롤링 내용
////    ScrapedResult scrapedResult = this.youtubeScraper.crawl();
////
////    // 스크래핑한 정보 중 없는 값을 저장
////    scrapedResult.getYoutubeDtoList().stream().map(youtubeDto -> Youtube.builder()
////        .videoId(youtubeDto.getVideoId())
////        .contentName(youtubeDto.getContentName())
////        .createdAt(youtubeDto.getCreatedAt())
////        .channelName(youtubeDto.getChannelName())
////        .updatedAt(youtubeDto.getUpdatedAt())
////        .length(youtubeDto.getLength())
////        .videoUrl(youtubeDto.getVideoUrl())
////        .imagePath(youtubeDto.getImagePath())
////        .hit(youtubeDto.getHit())
////        .updatedAt(LocalDateTime.now())
////        .build()
////    ).forEach(entity ->
////    {
////      boolean exists = this.youtubeRepository.existsById(entity.getVideoId());
////      if (!exists) {
////        this.youtubeRepository.save(entity);
////        log.info(entity.getContentName() + " is saved");
////        log.info(entity.getVideoUrl());
////      }
////    });
////  }
}
