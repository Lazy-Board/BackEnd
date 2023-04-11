package com.example.lazier.batch.tasklet;

import com.example.lazier.persist.entity.module.WeatherLocation;
import com.example.lazier.persist.repository.WeatherLocationRepository;
import com.example.lazier.service.module.WeatherService;
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
public class WeatherScrapTasklet implements Tasklet, StepExecutionListener {

    private final WeatherLocationRepository weatherLocationRepository;
    private final WeatherService weatherService;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("!!!Weather Scraping starts now!!!");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

        List<WeatherLocation> locationList = weatherLocationRepository.findAll();
        int count = 0;

        for (WeatherLocation location : locationList) {
            if (count == 60) {
                try {
                    Thread.sleep(60000);
                    count = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();;
                    Thread.currentThread().interrupt();
                }
            }
            weatherService.add(location);
            count++;

        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("!!!Weather Scraping finished!!!");
        return ExitStatus.COMPLETED;
    }


    public static void main(String[] args) {
        int count = 59;
        for (int i = 0; i < 10; i++) {
            if (count == 60) {
                try {
                    Thread.sleep(60000);
                    count = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();;
                    Thread.currentThread().interrupt();
                }
            }
            count++;
        }
    }
}
