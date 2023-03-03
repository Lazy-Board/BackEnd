package com.example.lazier.dto.user;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberModuleSaveRequestDto {

	@NotBlank
	private String userId;

	@NotBlank
	private boolean weatherYn;
	@NotBlank
	private boolean exchangeYn;
	@NotBlank
	private boolean stockYn;
	@NotBlank
	private boolean newsYn;
	@NotBlank
	private boolean youtubeYn;
	@NotBlank
	private boolean quoteYn;
	@NotBlank
	private boolean todolistYn;
	@NotBlank
	private boolean workYn;
}
