package com.example.lazier.persist.entity.module;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "UPDATE_EXCHANGE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LazierUser lazierUser;
    private String currencyName;
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
