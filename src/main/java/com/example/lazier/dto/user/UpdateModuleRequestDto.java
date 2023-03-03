package com.example.lazier.dto.user;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateModuleRequestDto {

	@NotBlank
	private String userModuleList;
}
