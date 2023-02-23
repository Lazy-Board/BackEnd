package com.example.lazier.dto.module;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrafficInput {

    @NotBlank
    String startingPoint;
    @NotBlank
    String destination;
}
