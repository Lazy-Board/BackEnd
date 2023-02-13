package com.example.lazier.exchangeModule.persist.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "PartialExchange")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PartialExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;                  // 유저ID
    private String currencyName;            // 통화명
    private String tradingStandardRate;     // 매매기준율
    private String comparedPreviousDay;     // 전일대비
    private String fluctuationRate;         // 등락율

}
