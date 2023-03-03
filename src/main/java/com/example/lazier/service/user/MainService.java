package com.example.lazier.service.user;

import com.example.lazier.dto.user.MemberModuleResponseDto;
import com.example.lazier.persist.entity.user.ModuleYn;
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

	//불러오기
	public MemberModuleResponseDto search(HttpServletRequest request) {
		ModuleYn moduleYn = moduleYnRepository.findAllByUserId(myPageService.parseUserId(request));

		return MemberModuleResponseDto.of(moduleYn);
	}

}
