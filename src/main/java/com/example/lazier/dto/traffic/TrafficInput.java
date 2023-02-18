package com.example.lazier.dto.traffic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrafficInput {
    String userId;
    String startingPoint;
    String destination;
}
