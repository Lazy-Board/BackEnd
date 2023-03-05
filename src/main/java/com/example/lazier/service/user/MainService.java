package com.example.lazier.service.user;

import com.example.lazier.dto.user.ModuleResponseDto;
import com.example.lazier.persist.entity.module.ModuleYn;
import com.example.lazier.persist.repository.ModuleYnRepository;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MainService {

	private final MyPageService myPageService;
	private final ModuleYnRepository moduleYnRepository;

	//모듈 불러오기
	public ModuleResponseDto searchModule(HttpServletRequest request) {
		ModuleYn moduleYn = moduleYnRepository.findAllByUserId(myPageService.parseUserId(request));
		return ModuleResponseDto.of(moduleYn);
	}
}
