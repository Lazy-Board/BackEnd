package com.example.lazier.dto.module;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewsUserInput {
    long userId;
    @NotBlank
    String press1;
    @NotBlank
    String press2;
    @NotBlank
    String press3;

}
