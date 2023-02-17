package com.example.lazier.service;

import com.example.lazier.dto.module.UserQuotesDto;
import com.example.lazier.dto.module.UserQuotesInput;

public interface UserQuoteService {

    void add(UserQuotesInput parameter);

    UserQuotesDto get(String userId);

    void update(UserQuotesInput parameter);

    void delete(String userId);
}
