package com.example.lazier.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePasswordRequestDto {
	private String password;
	private String newPassword;
}
