package com.example.lazier.dto.user;

import lombok.Data;

@Data
public class UpdatePasswordRequestDto {

	private String password;
	private String newPassword;
}
