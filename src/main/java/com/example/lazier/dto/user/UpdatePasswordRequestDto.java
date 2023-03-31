package com.example.lazier.dto.user;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePasswordRequestDto {

	@NotBlank
	private String password;

	@NotBlank
	private String newPassword;
}
