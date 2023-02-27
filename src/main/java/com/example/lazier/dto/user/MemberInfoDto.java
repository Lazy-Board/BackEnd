package com.example.lazier.dto.user;


import com.example.lazier.persist.entity.user.LazierUser;
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

	public static MemberInfoDto of(LazierUser lazierUser) {
		return MemberInfoDto.builder()
			.userEmail(lazierUser.getUserEmail())
			.userName(lazierUser.getName())
			.phoneNumber(lazierUser.getPhoneNumber())
			.socialType(lazierUser.getSocialType().toLowerCase().trim()) //google이라면 email 비활성
			.build();
	}
}
