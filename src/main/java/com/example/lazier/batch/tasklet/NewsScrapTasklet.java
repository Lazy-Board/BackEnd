package com.example.lazier.batch.tasklet;

import com.example.lazier.persist.entity.module.News;
import com.example.lazier.persist.entity.module.NewsPress;
import com.example.lazier.persist.repository.NewsPressRepository;
import com.example.lazier.persist.repository.NewsRepository;
import com.example.lazier.scraper.NewsScraper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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
public class NewsScrapTasklet implements Tasklet, StepExecutionListener {

    private final NewsRepository newsRepository;
    private final NewsPressRepository newsPressRepository;
    private final NewsScraper newsScraper;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("!!!News Scraping starts now!!!");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
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

        List<News> existNewsList = newsRepository.findAll();
        List<News> newNewsList = new ArrayList<>();
        pressIdList.forEach(
            id -> newsScraper.crawlNewsByPressAndDate(id, todayString).stream().map(News::new).forEach(
                newNewsList::add));
        for (News newNews : newNewsList) {
            for(News oldNews : existNewsList) {
                if (Objects.equals(newNews.getNewsId(), oldNews.getNewsId()))
                    break;
            }
            newsRepository.save(newNews);
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("!!!News Scraping finished!!!");
        return ExitStatus.COMPLETED;
    }

}

