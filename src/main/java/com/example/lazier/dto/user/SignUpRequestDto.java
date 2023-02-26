package com.example.lazier.dto.user;

import io.swagger.annotations.ApiParam;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

	@Email
	@NotBlank
	String userEmail;

	@NotBlank
	String userName;

	@Size(min = 8)
	@NotBlank
	String password;

	@NotBlank
	@Pattern(regexp = "[0-9]{10,11}", message = "010-xxxx-xxxx 형식으로 입력해주세요.")
	@ApiParam(value = "사용자 전화번호")
	String phoneNumber;

}
