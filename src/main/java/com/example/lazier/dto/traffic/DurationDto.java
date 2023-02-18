package com.example.lazier.dto.traffic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DurationDto {

    private String startingPoint;
    private String destination;
    private String duration;

}
