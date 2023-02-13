package com.example.lazier.QuotesModule.service.Impl;

import com.example.lazier.QuotesModule.model.UserQuotesInput;
import com.example.lazier.QuotesModule.persist.entity.UserQuotes;
import com.example.lazier.QuotesModule.persist.repository.UserQuotesRepository;
import com.example.lazier.QuotesModule.service.UserQuoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UseQuotesServiceImpl implements UserQuoteService {

    private final UserQuotesRepository userQuotesRepository;

    @Override
    @Transactional
    public void add(UserQuotesInput parameter) {
        // 중복 아이디 예외
        if (userQuotesRepository.existsById(parameter.getUserId())) {
            // 예외처리 할 예정입니다.
            throw new RuntimeException("사용자 정보가 존재합니다.");
        }

        UserQuotes userQuotes = UserQuotes.builder()
            .userId(parameter.getUserId())
            .content(parameter.getContent())
            .build();

        userQuotesRepository.save(userQuotes);
    }
}
