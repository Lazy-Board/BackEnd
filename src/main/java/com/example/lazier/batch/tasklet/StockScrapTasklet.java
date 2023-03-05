package com.example.lazier.batch.tasklet;

import com.example.lazier.service.module.StockService;
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
public class StockScrapTasklet implements Tasklet, StepExecutionListener {

    private final StockService stockService;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("!!!Stock Scraping starts now!!!");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        stockService.delete();
        stockService.add();
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("!!!Stock Scraping finished!!!");
        return ExitStatus.COMPLETED;
    }

}
