package com.example.lazier.persist.entity.module;

import com.example.lazier.persist.entity.user.LazierUser;
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

@Entity(name = "DETAIL_STOCK")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetailStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LazierUser lazierUser;

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
