package com.example.lazier.dto.user;


import com.example.lazier.persist.entity.module.LazierUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {

	String userEmail;
	String userName;
	String phoneNumber;
	String socialType;
	String profile;

	public static MemberInfoDto of(LazierUser lazierUser) {
		return MemberInfoDto.builder()
			.profile(lazierUser.getProfile())
			.userEmail(lazierUser.getUserEmail())
			.userName(lazierUser.getLazierName())
			.phoneNumber(lazierUser.getPhoneNumber())
			.socialType(lazierUser.getSocialType().toLowerCase().trim()) //google이라면 email 비활성
			.build();
	}
}
