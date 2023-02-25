package com.example.lazier.dto.module;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficInput {

    @NotBlank
    String startingPoint;
    @NotBlank
    String destination;
}
