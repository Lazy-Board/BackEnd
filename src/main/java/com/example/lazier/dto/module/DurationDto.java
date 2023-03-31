package com.example.lazier.dto.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DurationDto {

    private String startingPoint;
    private String destination;
    private String duration;

}
