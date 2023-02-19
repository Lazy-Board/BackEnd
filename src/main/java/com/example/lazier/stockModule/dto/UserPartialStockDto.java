package com.example.lazier.stockModule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPartialStockDto {

    private String stockName;                       // 종목명
    private String price;                           // 현재가
    private String diffAmount;                      // 전일비
    private String dayRange;                        // 등락률
    private String updateAt;

}
