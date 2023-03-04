package com.example.lazier.batch.config;

import com.example.lazier.batch.tasklet.NewsDeleteTasklet;
import com.example.lazier.batch.tasklet.PressScrapTasklet;
import com.example.lazier.batch.tasklet.WeatherDeleteTasklet;
import com.example.lazier.batch.tasklet.YoutubeDeleteTasklet;
import com.example.lazier.persist.repository.NewsPressRepository;
import com.example.lazier.persist.repository.NewsRepository;
import com.example.lazier.persist.repository.WeatherRepository;
import com.example.lazier.persist.repository.YoutubeRepository;
import com.example.lazier.scraper.NewsScraper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class DailyJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;
    private final WeatherRepository weatherRepository;
    private final NewsRepository newsRepository;
    private final YoutubeRepository youtubeRepository;
    private final NewsScraper newsScraper;
    private final NewsPressRepository newsPressRepository;


    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleDailyBatchJob() throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("JobID", String.valueOf(System.currentTimeMillis()))
            .toJobParameters();
        jobLauncher.run(dailyJob(), jobParameters);
    }

    @Bean
    public TaskExecutor dailyTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setQueueCapacity(30);
        return taskExecutor;
    }

    @Bean
    public Job dailyJob () {
        return jobBuilderFactory.get("dailyJob")
            .incrementer(new RunIdIncrementer())
            .start(newsDelete())
            .next(youtubeDelete())
            .next(weatherDelete())
            .next(pressScrap())
            .build();
    }

    @Bean
    @JobScope
    public Step newsDelete() {
        return this.stepBuilderFactory.get("newsDelete")
            .tasklet(new NewsDeleteTasklet(newsRepository))
            .build();
    }

    @Bean
    @JobScope
    public Step youtubeDelete() {
        return this.stepBuilderFactory.get("youtubeDelete")
            .tasklet(new YoutubeDeleteTasklet(youtubeRepository))
            .build();
    }

    @Bean
    @JobScope
    public Step weatherDelete() {
        return this.stepBuilderFactory.get("weatherDelete")
            .tasklet(new WeatherDeleteTasklet(weatherRepository))
            .build();
    }

    @Bean
    @JobScope
    public Step pressScrap() {
        return this.stepBuilderFactory.get("pressScrap")
            .tasklet(new PressScrapTasklet(newsPressRepository, newsScraper))
            .build();
    }
}
