package com.example.lazier.dto.module;

import com.example.lazier.persist.entity.module.UserQuotes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQuotesDto {

    private long userId;
    private String content;

    public static UserQuotesDto of(UserQuotes userQuotes) {
        return UserQuotesDto.builder()
            .userId(userQuotes.getLazierUser().getUserId())
            .content(userQuotes.getContent())
            .build();
    }
}
