package com.dyks.aihubbe.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dyks.aihubbe.domain.user.domain.User;
import com.dyks.aihubbe.domain.user.dto.UserProfileRequestDto;
import com.dyks.aihubbe.domain.user.dto.UserProfileResponseDto;
import com.dyks.aihubbe.domain.user.dto.UserSignUpRequestDto;
import com.dyks.aihubbe.domain.user.exception.UserAlreadyExistsException;
import com.dyks.aihubbe.domain.user.exception.UserNotFoundException;
import com.dyks.aihubbe.domain.user.repository.UserRepository;
import com.dyks.aihubbe.global.auth.jwt.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	//회원가입
	public String register(UserSignUpRequestDto userSignUpRequestDto){

		if (userRepository.findByEmail(userSignUpRequestDto.getEmail()).isPresent()) {
			throw new UserAlreadyExistsException();
		}
		if (userRepository.findByCustomId(userSignUpRequestDto.getCustomId()).isPresent()) {
			throw new UserAlreadyExistsException();
		}

		User user = User.create(userSignUpRequestDto.getCustomId(), userSignUpRequestDto.getEmail(),
			userSignUpRequestDto.getUserName(), userSignUpRequestDto.getPassword(),
			userSignUpRequestDto.getPhoneNumber(), userSignUpRequestDto.getBirth());

		user.passwordEncode(passwordEncoder);
		userRepository.save(user);

		return "회원가입 완료";
	}

	//본인 정보 조회
	public UserProfileResponseDto getProfile(HttpServletRequest request){
		User user = getUser(request);

		return UserProfileResponseDto.from(user, true);
	}

	// 사용자 정보 수정 메소드
	public void updateProfile(HttpServletRequest request, UserProfileRequestDto userProfileRequestDto) {
		User user = getUser(request);
		user.updateProfile(userProfileRequestDto.getUserName(), userProfileRequestDto.getPhoneNumber(),
			userProfileRequestDto.getCustomId(), userProfileRequestDto.getBirth(), userProfileRequestDto.getPassword());
	}

	//JWT 토큰 해독하여 사용자 정보 반환 메소드
	private User getUser(HttpServletRequest request) {
		User user = jwtService.extractAccessToken(request)
			.filter(jwtService::isTokenValid)
			.flatMap(jwtService::extractEmail)
			.flatMap(userRepository::findByEmail)
			.orElseThrow(UserNotFoundException::new);

		return user;
	}
}
