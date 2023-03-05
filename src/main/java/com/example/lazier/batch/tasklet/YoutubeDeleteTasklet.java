package com.example.lazier.batch.tasklet;

import com.example.lazier.persist.entity.module.Youtube;
import com.example.lazier.persist.repository.YoutubeRepository;
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
public class YoutubeDeleteTasklet implements Tasklet, StepExecutionListener {

    private final YoutubeRepository youtubeRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("!!!Youtube Deleting starts now!!!");
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        List<Youtube> youtubeEntities =
            this.youtubeRepository.findAllByCreatedAtBefore(LocalDateTime.now().minusDays(7));

        youtubeRepository.deleteAll(youtubeEntities);
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("!!!Youtube Deleting finished!!!");
        return ExitStatus.COMPLETED;
    }
}
