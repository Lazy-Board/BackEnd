package com.example.lazier.batch.tasklet;

import com.example.lazier.persist.entity.module.NewsPress;
import com.example.lazier.persist.repository.NewsPressRepository;
import com.example.lazier.scraper.NewsScraper;
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
public class PressScrapTasklet implements Tasklet, StepExecutionListener {

    private final NewsPressRepository newsPressRepository;
    private final NewsScraper newsScraper;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("!!!Press Scraper starts now!!!");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        newsScraper.crawlPressList().stream().map(NewsPress::new).forEach(entity ->
            {
                boolean exists = newsPressRepository.existsById(entity.getPressId());
                if (!exists) {
                    newsPressRepository.save(entity);
                }
            }
        );
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("!!!Press Scraper finished!!!");
        return ExitStatus.COMPLETED;
    }
}
