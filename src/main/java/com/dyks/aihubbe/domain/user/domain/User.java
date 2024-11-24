package com.dyks.aihubbe.domain.user.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.dyks.aihubbe.domain.shared.BaseTimeEntity;
import com.dyks.aihubbe.domain.shared.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotNull
	@Column
	private String customId;

	@NotNull
	@Column(length = 100)
	private String userName;

	@NotNull
	private String password;

	private String refreshToken;

	@NotNull
	@Column(length = 50)
	private String email;

	@Column(length = 20)
	private String phoneNumber;

	@Column
	private LocalDate birth;

	@Column
	private LocalDateTime inactiveDate;

	@NotNull
	@Column
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@NotNull
	@Column
	@Enumerated(EnumType.STRING)
	private Role userRole;

	@Builder
	private User(String customId, String email, String userName, String password, String phoneNumber, Role userRole, LocalDate birth) {
		this.customId = customId;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.userStatus = UserStatus.ACTIVE;
		this.userRole = userRole;
		this.birth = birth;
	}

	public static User create(String customId, String email, String userName, String password, String phoneNumber, LocalDate birth) {
		return User.builder()
			.customId(customId)
			.email(email)
			.userName(userName)
			.password(password)
			.phoneNumber(phoneNumber)
			.userRole(Role.USER)
			.birth(birth)
			.build();
	}

	public void passwordEncode(PasswordEncoder passwordEncoder) { //비밀번호 암호화 메소드
		this.password = passwordEncoder.encode(this.password);
	}

	public void updateProfile(String userName, String phoneNumber, String customId, LocalDate birth,String password) {
		this.userName = userName;
		this.customId = customId;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.birth = birth;
	}

	public void updateRefreshToken(String updateRefreshToken) {
		this.refreshToken = updateRefreshToken;
	}
}
