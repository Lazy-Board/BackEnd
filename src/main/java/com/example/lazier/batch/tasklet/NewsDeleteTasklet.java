package com.example.lazier.batch.tasklet;

import com.example.lazier.persist.entity.module.News;
import com.example.lazier.persist.repository.NewsRepository;
import java.time.LocalDateTime;
import java.util.List;
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
public class NewsDeleteTasklet implements Tasklet, StepExecutionListener {

    private final NewsRepository newsRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("!!!News Deleting starts now!!!");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        List<News> newsEntites = this.newsRepository.findAllByUpdatedAtBefore(
            LocalDateTime.now().minusDays(2));
        newsRepository.deleteAll(newsEntites);
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("!!!News Deleting finished!!!");
        return ExitStatus.COMPLETED;
    }
}
