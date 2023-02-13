package com.example.lazier.QuotesModule.service;

import com.example.lazier.QuotesModule.dto.UserQuotesDto;
import com.example.lazier.QuotesModule.model.UserQuotesInput;

public interface UserQuoteService {

    void add(UserQuotesInput parameter);

    UserQuotesDto get(String userId);

    void update(UserQuotesInput parameter);
}
