package com.example.lazier.stockModule.persist.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Kospi")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Kospi {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    private String stockName;                       // 종목명
    private String price;                           // 현재가
    private String diffAmount;                      // 전일비
    private String dayRange;                        // 등락률
    private String parValue;                        // 액면가
    private String turnover;                        // 거래량
    private String numberOfListedShares;            // 상장주식수
    private String marketCap;                       // 시가총액
    private String foreignOwnRate;                  // 외국인비율
    private String per;                             // PER (주가수익률)
    private String roe;                             // ROE (자기자본이익률)

}
