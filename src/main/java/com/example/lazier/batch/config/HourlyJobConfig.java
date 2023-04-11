package com.example.lazier.batch.config;

import com.example.lazier.batch.tasklet.ExchangeScrapTasklet;
import com.example.lazier.batch.tasklet.NewsScrapTasklet;
import com.example.lazier.batch.tasklet.StockScrapTasklet;
import com.example.lazier.batch.tasklet.WeatherScrapTasklet;
import com.example.lazier.batch.tasklet.YoutubeScrapTasklet;
import com.example.lazier.persist.repository.NewsPressRepository;
import com.example.lazier.persist.repository.NewsRepository;
import com.example.lazier.persist.repository.WeatherLocationRepository;
import com.example.lazier.persist.repository.YoutubeRepository;
import com.example.lazier.scraper.NewsScraper;
import com.example.lazier.scraper.YoutubeScraper;
import com.example.lazier.service.module.ExchangeService;
import com.example.lazier.service.module.StockService;
import com.example.lazier.service.module.WeatherService;
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
public class HourlyJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobLauncher jobLauncher;
    private final WeatherLocationRepository weatherLocationRepository;
    private final WeatherService weatherService;
    private final StockService stockService;
    private final ExchangeService exchangeService;
    private final NewsRepository newsRepository;
    private final NewsPressRepository newsPressRepository;
    private final NewsScraper newsScraper;
    private final YoutubeRepository youtubeRepository;
    private final YoutubeScraper youtubeScraper;

    @Scheduled(cron = "0 0 0/1 * * *")
    public void scheduleHourlyBatchJob() throws JobExecutionException {
        JobParameters jobParameters = new JobParametersBuilder()
            .addString("JobID", String.valueOf(System.currentTimeMillis()))
            .toJobParameters();
        jobLauncher.run(hourlyJob(), jobParameters);
    }

    @Bean
    public TaskExecutor hourlyTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setQueueCapacity(30);
        return taskExecutor;
    }

    @Bean
    public Job hourlyJob() {
        return jobBuilderFactory.get("hourlyJob")
            .incrementer(new RunIdIncrementer())
            .start(weatherScrap()).on("*").to(stockScrap())
            .from(stockScrap()).on("*").to(exchangeScrap())
            .from(exchangeScrap()).on("*").to(newsScrap())
            .from(newsScrap()).on("*").to(youtubeScrap())
            .end()
            .build();
    }

    @Bean
    @JobScope
    public Step weatherScrap() {
        return this.stepBuilderFactory.get("weatherScrap")
            .tasklet(new WeatherScrapTasklet(weatherLocationRepository, weatherService))
            .build();
    }

    @Bean
    @JobScope
    public Step stockScrap() {
        return this.stepBuilderFactory.get("stockScrap")
            .tasklet(new StockScrapTasklet(stockService))
            .build();
    }

    @Bean
    @JobScope
    public Step exchangeScrap() {
        return this.stepBuilderFactory.get("exchangeScrap")
            .tasklet(new ExchangeScrapTasklet(exchangeService))
            .build();
    }

    @Bean
    @JobScope
    public Step newsScrap() {
        return this.stepBuilderFactory.get("newsScrap")
            .tasklet(new NewsScrapTasklet(newsRepository, newsPressRepository, newsScraper))
            .build();
    }

    @Bean
    @JobScope
    public Step youtubeScrap() {
        return this.stepBuilderFactory.get("youtubeScrap")
            .tasklet(new YoutubeScrapTasklet(youtubeRepository, youtubeScraper))
            .build();
    }
}
