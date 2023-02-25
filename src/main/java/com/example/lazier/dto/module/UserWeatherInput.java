package com.example.lazier.dto.module;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWeatherInput {
    @NotBlank
    String cityName;
    @NotBlank
    String locationName;

}
