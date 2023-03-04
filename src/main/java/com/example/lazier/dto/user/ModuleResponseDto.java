package com.example.lazier.dto.user;

import com.example.lazier.persist.entity.user.ModuleYn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleResponseDto {

	private boolean weatherYn;
	private boolean exchangeYn;
	private boolean stockYn;
	private boolean newsYn;
	private boolean youtubeYn;
	private boolean quoteYn;
	private boolean todolistYn;
	private boolean workYn;

	public static ModuleResponseDto of(ModuleYn moduleYn) {
		return ModuleResponseDto
			.builder()
			.weatherYn(moduleYn.isWeatherYn())
			.exchangeYn(moduleYn.isExchangeYn())
			.stockYn(moduleYn.isStockYn())
			.newsYn(moduleYn.isNewsYn())
			.youtubeYn(moduleYn.isYoutubeYn())
			.quoteYn(moduleYn.isQuoteYn())
			.todolistYn(moduleYn.isTodolistYn())
			.workYn(moduleYn.isWorkYn())
			.build();
	}
}
