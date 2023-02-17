package com.example.lazier.QuotesModule.service.Impl;

import com.example.lazier.QuotesModule.dto.UserQuotesDto;
import com.example.lazier.QuotesModule.exception.UserAlreadyExistException;
import com.example.lazier.QuotesModule.exception.UserNotFoundException;
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
            throw new UserAlreadyExistException("사용자 정보가 존재합니다.");
        }

        UserQuotes userQuotes = UserQuotes.builder().userId(parameter.getUserId())
            .content(parameter.getContent()).build();

        userQuotesRepository.save(userQuotes);
    }

    @Override
    @Transactional(readOnly = true)
    public UserQuotesDto get(String userId) {
        UserQuotes userQuotes = userQuotesRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));
        return UserQuotesDto.of(userQuotes);
    }

    @Override
    @Transactional
    public void update(UserQuotesInput parameter) {
        UserQuotes userQuotes = userQuotesRepository.findById(parameter.getUserId())
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userQuotes.update(parameter.getContent());
    }

    @Override
    @Transactional
    public void delete(String userId) {
        UserQuotes userQuotes = userQuotesRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("사용자 정보가 존재하지 않습니다."));

        userQuotesRepository.delete(userQuotes);
    }

}
