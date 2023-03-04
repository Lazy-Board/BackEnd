package com.example.lazier.batch.tasklet;

import com.example.lazier.dto.module.ScrapedResult;
import com.example.lazier.persist.entity.module.Youtube;
import com.example.lazier.persist.repository.YoutubeRepository;
import com.example.lazier.scraper.YoutubeScraper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@StepScope
@RequiredArgsConstructor
@Slf4j
public class YoutubeScrapTasklet implements Tasklet, StepExecutionListener {

    private final YoutubeRepository youtubeRepository;
    private final YoutubeScraper youtubeScraper;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("!!!Youtube Scraping starts now!!!");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        ScrapedResult scrapedResult = this.youtubeScraper.crawl();

        // 스크래핑한 정보 중 없는 값을 저장
        scrapedResult.getYoutubeDtoList().stream()
            .map(e -> Youtube.builder()
                .videoId(e.getVideoId())
                .contentName(e.getContentName())
                .createdAt(e.getCreatedAt())
                .channelName(e.getChannelName())
                .updatedAt(e.getUpdatedAt())
                .length(e.getLength())
                .videoUrl(e.getVideoUrl())
                .imagePath(e.getImagePath())
                .hit(e.getHit())
                .updatedAt(LocalDateTime.now())
                .build()
            )

            .forEach(e ->
            {
                boolean exists = this.youtubeRepository.existsById(e.getVideoId());
                if (!exists) {
                    this.youtubeRepository.save(e);
//                    System.out.println(e.getContentName() + " is saved");
                }
            });
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("!!!Youtube Scraping finished!!!");
        return ExitStatus.COMPLETED;
    }

}

