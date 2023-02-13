package com.example.lazier.QuotesModule.persist.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserQuotes {

    @Id
    private String userId;

    private String content;
}
