package com.example.lazier.dto.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAllStockDto {

    private String stockName;                       // 종목명
    private String price;                           // 현재가
    private String diffAmount;                      // 전일비
    private String dayRange;                        // 등락률
    private String marketPrice;                     // 시가
    private String highPrice;                       // 고가
    private String lowPrice;                        // 저가
    private String tradingVolume;                   // 거래량
    private String updateAt;

}
