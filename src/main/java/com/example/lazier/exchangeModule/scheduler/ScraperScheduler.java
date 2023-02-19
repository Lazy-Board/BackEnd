package com.example.lazier.exchangeModule.scheduler;

import com.example.lazier.exchangeModule.persist.entity.UserExchange;
import com.example.lazier.exchangeModule.persist.repository.UserExchangeRepository;
import com.example.lazier.exchangeModule.scraper.ExchangeScraper;
import com.example.lazier.exchangeModule.service.ExchangeService;
import java.security.Principal;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ScraperScheduler {
    private final ExchangeService exchangeService;

    // 일정 기간동안 수행
    @Scheduled(cron = "${scheduler.scrap.exchange}")
    public void exchangeScheduling() {
        log.info("scraping scheduler is started");
        exchangeService.add();
    }
}
