/*
package com.example.lazier.scheduler;

import com.example.lazier.service.module.ExchangeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ExchangeScraperScheduler {
    private final ExchangeService exchangeService;

    // 일정 기간동안 수행
    @Scheduled(cron = "${scheduler.scrap.exchange}")
    public void exchangeScheduling() {
        log.info("scraping exchange scheduler is started");
        exchangeService.delete();
        exchangeService.add();
    }
}
*/
