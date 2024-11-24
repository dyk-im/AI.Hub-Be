package com.dyks.aihubbe.domain.user.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignUpRequestDto {

	private String email;
	private String customId;
	private String userName;
	private String password;
	private String phoneNumber;
	private LocalDate birth;
}
