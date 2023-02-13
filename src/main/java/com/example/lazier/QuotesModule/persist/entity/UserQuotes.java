package com.example.lazier.QuotesModule.persist.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserQuotes {

    @Id
    private String userId;

    private String content;

    public void update(String content) {
        this.content = content;
    }
}
