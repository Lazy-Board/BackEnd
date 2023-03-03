package com.example.lazier.service.user;

import com.example.lazier.dto.user.DeleteOneModuleRequestDto;
import com.example.lazier.dto.user.ModuleResponseDto;
import com.example.lazier.dto.user.UpdateOneModuleRequestDto;
import com.example.lazier.persist.entity.user.ModuleYn;
import com.example.lazier.persist.repository.ModuleYnRepository;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MainService {

	private final MyPageService myPageService;
	private final ModuleYnRepository moduleYnRepository;

	//불러오기
	public ModuleResponseDto searchModule(HttpServletRequest request) {
		return ModuleResponseDto.of(searchModuleYn(request));
	}

	@Transactional
	public void deleteOneModule(HttpServletRequest request,
		DeleteOneModuleRequestDto deleteOneModuleRequestDto) {
		ModuleYn moduleYn = searchModuleYn(request);

		if (deleteOneModuleRequestDto.getDeleteModule().trim().equals("weather")) {
			moduleYn.setWeatherYn(false);
		}
	}

	@Transactional
	public void updateOneModule(HttpServletRequest request,
		UpdateOneModuleRequestDto updateOneModuleRequestDto) {
		ModuleYn moduleYn = searchModuleYn(request);
	}

	//모듈 리턴
	public ModuleYn searchModuleYn(HttpServletRequest request) {
		return moduleYnRepository.findAllByUserId(myPageService.parseUserId(request));
	}
}
