package com.example.lazier.scheduler;

import com.example.lazier.persist.entity.module.UserExchange;
import com.example.lazier.persist.repository.UserExchangeRepository;
import com.example.lazier.scraper.ExchangeScraper;
import com.example.lazier.service.ExchangeService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ExchangeScraperScheduler {
    private final ExchangeService exchangeService;
    private final ExchangeScraper exchangeScraper;
    private final UserExchangeRepository userExchangeRepository;
    private HttpServletRequest request;

    // 일정 기간동안 수행
    @Scheduled(cron = "${scheduler.scrap.exchange}")
    public void exchangeScheduling() {
        log.info("scraping scheduler is started");
        exchangeScraper.scrap();
        String userId = (String)request.getAttribute("userId");

        Optional<UserExchange> optionalUserExchange = userExchangeRepository.findById(userId);

        UserExchange userExchange = optionalUserExchange.get();

        if (userExchangeRepository.existsById(userExchange.getUserId())) {
            exchangeService.add();
        }

    }
}
