package com.example.lazier.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindPasswordRequestDto {

	@Email
	@NotBlank
	private String userEmail;

	@NotBlank
	@Pattern(regexp = "[0-9]{10,11}", message = "-제외하고 입력해주세요.")
	private String phoneNumber;

}
