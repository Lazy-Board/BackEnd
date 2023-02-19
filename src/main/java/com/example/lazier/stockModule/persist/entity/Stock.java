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

@Entity(name = "Stock")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String stockName;                       // 종목명
    private String price;                           // 현재가
    private String diffAmount;                      // 전일비
    private String dayRange;                        // 등락률
    private String marketPrice;                     // 시가
    private String highPrice;                       // 고가
    private String lowPrice;                        // 저가
    private String tradingVolume;                   // 거래량
    private String updateAt;                        // 업데이트 시간

}
