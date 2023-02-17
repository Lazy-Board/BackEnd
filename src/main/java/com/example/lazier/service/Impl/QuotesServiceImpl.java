package com.example.lazier.service.Impl;

import com.example.lazier.dto.module.QuotesDto;
import com.example.lazier.exception.QuotesException.WrongIdNumberException;
import com.example.lazier.persist.entity.module.Quotes;
import com.example.lazier.persist.repository.QuotesRepository;
import com.example.lazier.service.QuotesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class QuotesServiceImpl implements QuotesService {

    public final QuotesRepository quotesRepository;

    @Override
    @Transactional(readOnly = true)
    public QuotesDto get() {
        // DB에 10개만 저장 예정이여서 10개로 설정하였습니다.
        long randomNumber = (long) (Math.random() * 10 + 1);
        Quotes quotes = quotesRepository.findById(randomNumber)
            .orElseThrow(() -> new WrongIdNumberException("잘못된 아이디 정보 입니다."));
        return QuotesDto.of(quotes);
    }
}
