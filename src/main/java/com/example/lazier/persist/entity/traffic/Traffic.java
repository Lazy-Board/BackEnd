package com.example.lazier.persist.entity.traffic;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Traffic {

    @Id
    private String userId;

    private String startingPoint; // 출발지
    private String startingGeoCode; // 위경도 정보
    private String destination; // 도착지
    private String destinationGeoCode; // 위경도 정보

    public void update(String startingPoint, String destination, String startingGeoCode,
        String destinationGeoCode) {
        this.startingPoint = startingPoint;
        this.startingGeoCode = startingGeoCode;
        this.destination = destination;
        this.destinationGeoCode = destinationGeoCode;
    }
}