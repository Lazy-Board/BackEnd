package com.example.lazier.exchangeModule.persist.entity;

import com.example.lazier.exchangeModule.dto.ExchangeDto;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.beans.factory.annotation.Value;

@Entity(name = "EXCHANGE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String currencyName;            // 통화명
    private String countryName;             // 국가명
    private String tradingStandardRate;     // 매매기준율
    private String comparedPreviousDay;     // 전일대비
    private String fluctuationRate;         // 등락율
    private String buyCash;                 // 현찰 살 때
    private String sellCash;                // 현찰 팔 때
    private String sendMoney;               // 송금 보낼 때
    private String receiveMoney;            // 송금 받을 때
    private String updateAt;                // 업데이트 날짜
    private String round;                   // 고시회차

}
