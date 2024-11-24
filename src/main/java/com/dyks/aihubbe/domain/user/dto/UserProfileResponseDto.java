package com.dyks.aihubbe.domain.user.dto;

import com.dyks.aihubbe.domain.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserProfileResponseDto {
	private String email;
	private String customId;
	private String userName;
	private String phoneNumber;
	private Boolean isMyProfile;

	@Builder
	private UserProfileResponseDto(String email, String customId, String userName, String phoneNumber, Boolean isMyProfile) {
		this.email = email;
		this.customId = customId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.isMyProfile = isMyProfile;
	}

	public static UserProfileResponseDto from(User user, Boolean isMyProfile) {
		return UserProfileResponseDto.builder()
			.email(user.getEmail())
			.customId(user.getCustomId())
			.userName(user.getUserName())
			.phoneNumber(user.getPhoneNumber())
			.isMyProfile(isMyProfile)
			.build();
	}
}
