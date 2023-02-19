package com.example.lazier.dto.module;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserQuotesInput {

    @NotBlank
    String content;
}
