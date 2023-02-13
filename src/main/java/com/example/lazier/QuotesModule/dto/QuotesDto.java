package com.example.lazier.QuotesModule.dto;

import com.example.lazier.QuotesModule.persist.entity.Quotes;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuotesDto {

    private long quotesId;
    private String content;
    private String writer;

    public static QuotesDto of(Quotes quotes) {
        return QuotesDto.builder()
            .quotesId(quotes.getQuotesId())
            .content(quotes.getContent())
            .writer(quotes.getWriter())
            .build();
    }
}
