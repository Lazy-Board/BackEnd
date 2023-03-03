package com.example.lazier.persist.entity.user;

import com.example.lazier.dto.user.MemberModuleSaveRequestDto;
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

	public static ModuleYn of(LazierUser lazierUser,
		MemberModuleSaveRequestDto memberModuleSaveRequestDto) {

		return ModuleYn.builder()
			.lazierUser(lazierUser)
			.weatherYn(memberModuleSaveRequestDto.isWeatherYn())
			.exchangeYn(memberModuleSaveRequestDto.isExchangeYn())
			.stockYn(memberModuleSaveRequestDto.isStockYn())
			.newsYn(memberModuleSaveRequestDto.isNewsYn())
			.youtubeYn(memberModuleSaveRequestDto.isYoutubeYn())
			.quoteYn(memberModuleSaveRequestDto.isQuoteYn())
			.todolistYn(memberModuleSaveRequestDto.isTodolistYn())
			.workYn(memberModuleSaveRequestDto.isWorkYn())
			.build();
	}
}
