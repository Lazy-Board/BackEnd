package com.example.lazier.stockModule.persist.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "USER_STOCK")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserStock {
    @Id
    private String userId;
    private String samsungElectronic;
    private String skHynix;
    private String naver;
    private String kakao;
    private String hyundaiCar;
    private String kia;
    private String lgElectronic;
    private String kakaoBank;
    private String samsungSdi;
    private String hive;

}
