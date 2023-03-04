package com.example.lazier.batch.tasklet;

import com.example.lazier.persist.entity.module.Weather;
import com.example.lazier.persist.repository.WeatherRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class WeatherDeleteTasklet implements Tasklet, StepExecutionListener {

    private final WeatherRepository weatherRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("!!!Weather Deleting starts now!!!");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        List<Weather> oldData = weatherRepository.findAllByUpdatedAtBefore(
            dateText(LocalDateTime.now().minusDays(2)));
        weatherRepository.deleteAll(oldData);

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("!!!Weather Deleting finished!!!");
        return ExitStatus.COMPLETED;
    }

    public String dateText(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return now != null ? now.format(formatter) : "";
    }
}
