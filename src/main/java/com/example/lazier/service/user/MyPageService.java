package com.example.lazier.service.user;

import com.example.lazier.component.MailComponents;
import com.example.lazier.dto.user.FindPasswordRequestDto;
import com.example.lazier.dto.user.MemberInfoDto;
import com.example.lazier.dto.user.SignUpResponseDto;
import com.example.lazier.dto.user.UpdateModuleRequestDto;
import com.example.lazier.dto.user.UpdatePasswordRequestDto;
import com.example.lazier.dto.user.UpdateResponseDto;
import com.example.lazier.exception.user.FailedFindPasswordException;
import com.example.lazier.exception.user.FailedSignUpException;
import com.example.lazier.exception.user.FailedUpdateException;
import com.example.lazier.exception.user.NotFoundMemberException;
import com.example.lazier.exception.user.NotMatchMemberException;
import com.example.lazier.persist.entity.module.LazierUser;
import com.example.lazier.persist.entity.module.ModuleYn;
import com.example.lazier.persist.repository.MemberRepository;
import com.example.lazier.persist.repository.ModuleYnRepository;
import com.example.lazier.service.module.NewsUserService;
import com.example.lazier.service.module.UserExchangeService;
import com.example.lazier.service.module.UserStockService;
import com.example.lazier.type.MemberStatus;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MyPageService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final RedisService redisService;
	private final MailComponents mailComponents;
	private final ModuleYnRepository moduleYnRepository;

	private final UserExchangeService userExchangeService;
	private final UserStockService userstockService;
	private final NewsUserService newsUserService;

	public MemberInfoDto showUserInfo(HttpServletRequest request) {
		LazierUser lazierUser = searchMember(parseUserId(request));
		return MemberInfoDto.of(lazierUser);
	}


	@Transactional
	public UpdateResponseDto updateUserInfo(HttpServletRequest request, MemberInfoDto memberInfoDto) {

		LazierUser lazierUser = searchMember(parseUserId(request));
		String uuid = UUID.randomUUID().toString();

		//본인 이메일이 아니라면
		if (!lazierUser.getUserEmail().equals(memberInfoDto.getUserEmail())) {
			//이메일 중복
			if (memberRepository.existsByUserEmail(memberInfoDto.getUserEmail())) {
				throw new FailedUpdateException("이미 가입된 이메일입니다.");
			}
			//이메일 중복이 아니라면 인증
			lazierUser.setUserEmail(memberInfoDto.getUserEmail());
			lazierUser.setEmailAuthKey(uuid);
			lazierUser.setEmailAuthYn(false);
			lazierUser.setUserStatus(MemberStatus.STATUS_READY.getUserStatus());

			String email = memberInfoDto.getUserEmail();
			String title = "Lazier 가입을 축하드립니다.";
			String contents = "아래 링크를 클릭하여 가입을 완료하세요." +
				"<p>" +
				"<a target='_blank' href='http://3.34.73.141:8080/user/email-auth?uuid=" + uuid + "'>가입완료</a>" +
				"</p>";

			boolean sendEmail = mailComponents.sendEmail(email, title, contents);
			if (!sendEmail) { throw new FailedUpdateException("메일 전송에 실패하였습니다."); }

			return UpdateResponseDto.builder() //for test
				.uuid(uuid)
				.message("이메일 인증을 완료하세요")
				.build();
		}

		//본인 이메일이라면
		lazierUser.setName(memberInfoDto.getUserName());
		lazierUser.setPhoneNumber(memberInfoDto.getPhoneNumber());

		return UpdateResponseDto.builder() //for test
			.message("수정 완료")
			.build();
	}

	@Transactional
	public void updatePassword(HttpServletRequest request, UpdatePasswordRequestDto passwordDto) {
		LazierUser lazierUser = searchMember(parseUserId(request));

		if (!passwordEncoder.matches(passwordDto.getPassword(), lazierUser.getPassword())) {
			throw new NotMatchMemberException("비밀번호를 다시 입력하세요.");
		}
		lazierUser.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
	}


	//사용자 정보(이메일, 전화번호)가 일치하면 -> 이메일로 임시 비밀번호 전송
	@Transactional
	public void findPassword(FindPasswordRequestDto passwordDto) {
		log.info("비밀번호 찾기 - 이메일: " + passwordDto.getUserEmail());

		LazierUser lazierUser = memberRepository.findByUserEmail(passwordDto.getUserEmail())
			.orElseThrow(() -> new NotFoundMemberException("사용자 정보가 없습니다"));

		if (!lazierUser.getUserEmail().equals(passwordDto.getUserEmail())
			|| !lazierUser.getPhoneNumber().equals(passwordDto.getPhoneNumber())) {
			throw new NotFoundMemberException("이메일 또는 전화번호가 일치하지 않습니다.");
		}

		String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
		lazierUser.setPassword(passwordEncoder.encode(uuid));

		String email = lazierUser.getUserEmail();
		String title = "Lazier 새 비밀번호 발급";
		String contents = "임시 비밀번호 : " + uuid;

		boolean sendEmail = mailComponents.sendEmail(email, title, contents);
		if (!sendEmail) {
			throw new FailedFindPasswordException("메일 전송에 실패하였습니다.");
		}
	}

	@Transactional
	public void withdrawal(HttpServletRequest request) {
		LazierUser lazierUser = searchMember(parseUserId(request));
		redisService.delValues(request);

		lazierUser.setUserStatus(MemberStatus.STATUS_WITHDRAW.getUserStatus());
	}

	public LazierUser searchMember(Long userId) {
		return memberRepository.findByUserId(userId)
			.orElseThrow(() -> new NotFoundMemberException("사용자 정보가 없습니다."));
	}

	public Long parseUserId(HttpServletRequest request) {
		return Long.valueOf(request.getAttribute("userId").toString());
	}

	//모듈 업데이트
	@Transactional
	public void updateModule(HttpServletRequest request,
		UpdateModuleRequestDto updateModuleRequestDto) {
		ModuleYn moduleYn = moduleYnRepository.findByUserId(parseUserId(request));

		if (updateModuleRequestDto.isNewsYn()) {
			newsUserService.add(request.getAttribute("userId").toString());
		}

		if (updateModuleRequestDto.isStockYn()) {
			userstockService.add(request.getAttribute("userId").toString());
		}

		if (updateModuleRequestDto.isExchangeYn()) {
			userExchangeService.add(request.getAttribute("userId").toString());
		}

		moduleYn.setWeatherYn(updateModuleRequestDto.isWeatherYn());
		moduleYn.setExchangeYn(updateModuleRequestDto.isExchangeYn());
		moduleYn.setStockYn(updateModuleRequestDto.isStockYn());
		moduleYn.setNewsYn(updateModuleRequestDto.isNewsYn());
		moduleYn.setYoutubeYn(updateModuleRequestDto.isYoutubeYn());
		moduleYn.setQuoteYn(updateModuleRequestDto.isQuoteYn());
		moduleYn.setTodolistYn(updateModuleRequestDto.isTodolistYn());
		moduleYn.setWorkYn(updateModuleRequestDto.isWorkYn());
	}

}
