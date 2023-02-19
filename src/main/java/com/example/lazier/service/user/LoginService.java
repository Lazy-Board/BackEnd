package com.example.lazier.service.user;

import com.example.lazier.exception.user.NotFoundMemberException;
import com.example.lazier.persist.entity.user.LazierUser;
import com.example.lazier.persist.repository.MemberRepository;
import com.example.lazier.type.MemberStatus;
import com.example.lazier.exception.user.FailedLoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class LoginService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {

		LazierUser lazierUser = memberRepository.findByUserId(Long.valueOf(username))
			.orElseThrow(() -> new NotFoundMemberException("사용자 정보가 없습니다."));

		if (MemberStatus.STATUS_READY.getUserStatus().equals(lazierUser.getUserStatus())) {
			throw new FailedLoginException("이메일 활성화 이후에 로그인 하세요.");
		}

		if (MemberStatus.STATUS_WITHDRAW.getUserStatus().equals(lazierUser.getUserStatus())) {
			throw new FailedLoginException("탈퇴한 회원입니다.");
		}
		return lazierUser;
	}
}
