package com.example.lazier.dto.module;

import com.example.lazier.persist.entity.module.Quotes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuotesDto {

    private String content;
    private String writer;

    public static QuotesDto of(Quotes quotes) {
        return QuotesDto.builder()
            .content(quotes.getContent())
            .writer(quotes.getWriter())
            .build();
    }
}
