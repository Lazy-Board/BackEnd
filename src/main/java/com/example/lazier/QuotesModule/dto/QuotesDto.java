package com.example.lazier.QuotesModule.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuotesDto {

    private long quotesId;
    private String content;
    private String writer;

}
