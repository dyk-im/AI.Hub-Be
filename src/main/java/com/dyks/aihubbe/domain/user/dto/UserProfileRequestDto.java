package com.dyks.aihubbe.domain.user.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserProfileRequestDto {
	private String customId;
	private String userName;
	private String phoneNumber;
	private String password;
	private LocalDate birth;
}
