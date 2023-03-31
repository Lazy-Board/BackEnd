package com.example.lazier.dto.module;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewsUserInput {
    long userId;
    @NotBlank
    String press1;
}
