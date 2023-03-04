package com.example.lazier.dto.user;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateModuleRequestDto {

	@NotNull
	private boolean weatherYn;
	@NotNull
	private boolean exchangeYn;
	@NotNull
	private boolean stockYn;
	@NotNull
	private boolean newsYn;
	@NotNull
	private boolean youtubeYn;
	@NotNull
	private boolean quoteYn;
	@NotNull
	private boolean todolistYn;
	@NotNull
	private boolean workYn;
}
