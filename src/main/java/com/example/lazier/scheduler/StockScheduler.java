package com.example.lazier.scheduler;

import com.example.lazier.service.module.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class StockScheduler {
    private final StockService stockService;

    // 일정 기간동안 수행
    @Scheduled(cron = "${scheduler.scrap.stock}")
    public void exchangeScheduling() {
        log.info("scraping scheduler is started");
        stockService.delete();
        stockService.add();
    }
}
