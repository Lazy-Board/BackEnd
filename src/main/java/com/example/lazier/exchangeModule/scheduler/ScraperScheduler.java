package com.example.lazier.exchangeModule.scheduler;

import com.example.lazier.exchangeModule.dto.ExchangeDto;
import com.example.lazier.exchangeModule.dto.PartialExchangeDto;
import com.example.lazier.exchangeModule.service.ExchangeService;
import com.example.lazier.exchangeModule.service.PartialExchangeService;
import java.security.Principal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ScraperScheduler {
    private final ExchangeService exchangeService;
    private final PartialExchangeService partialExchangeService;

    // 일정 기간동안 수행
    @Scheduled(cron = "${scheduler.scrap.exchange}")
    public void exchangeScheduling() {
        Principal principal = null;
        String userId = principal.getName();
        log.info("scraping scheduler is started");
        ExchangeDto exchangeDto = new ExchangeDto();
        PartialExchangeDto partialExchangeDto = new PartialExchangeDto();
        exchangeService.updateRealTimeExchange(userId, exchangeDto);
        partialExchangeService.updateRealTimePartialExchange(userId, partialExchangeDto);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
