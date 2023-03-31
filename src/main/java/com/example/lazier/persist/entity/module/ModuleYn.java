package com.example.lazier.persist.entity.module;

import com.example.lazier.dto.user.SaveModuleRequestDto;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class ModuleYn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private LazierUser lazierUser;

	private boolean weatherYn;
	private boolean exchangeYn;
	private boolean stockYn;
	private boolean newsYn;
	private boolean youtubeYn;
	private boolean quoteYn;
	private boolean todolistYn;
	private boolean workYn;
	private boolean moduleCode;

	public static void save(ModuleYn moduleYn, SaveModuleRequestDto saveModuleRequestDto) {

		moduleYn.setWeatherYn(saveModuleRequestDto.isWeatherYn());
		moduleYn.setExchangeYn(saveModuleRequestDto.isExchangeYn());
		moduleYn.setStockYn(saveModuleRequestDto.isStockYn());
		moduleYn.setNewsYn(saveModuleRequestDto.isNewsYn());
		moduleYn.setYoutubeYn(saveModuleRequestDto.isYoutubeYn());
		moduleYn.setQuoteYn(saveModuleRequestDto.isQuoteYn());
		moduleYn.setTodolistYn(saveModuleRequestDto.isTodolistYn());
		moduleYn.setWeatherYn(saveModuleRequestDto.isWeatherYn());
		moduleYn.setModuleCode(true);
	}
}
