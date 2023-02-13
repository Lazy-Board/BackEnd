package com.example.lazier.QuotesModule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserQuotesDto {

    private String userId;
    private String content;

}
