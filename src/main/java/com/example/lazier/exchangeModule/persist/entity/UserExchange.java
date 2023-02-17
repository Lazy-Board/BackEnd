package com.example.lazier.exchangeModule.persist.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "USER_EXCHANGE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserExchange {
    @Id
    private String userId;
    private String usd;
    private String jpy;
    private String eur;
    private String cny;
    private String hkd;
    private String gbp;
    private String chf;
    private String cad;
    private String aud;
    private String nzd;

}
