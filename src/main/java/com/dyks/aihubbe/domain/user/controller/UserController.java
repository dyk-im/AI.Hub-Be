package com.dyks.aihubbe.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dyks.aihubbe.domain.user.dto.UserProfileRequestDto;
import com.dyks.aihubbe.domain.user.dto.UserProfileResponseDto;
import com.dyks.aihubbe.domain.user.service.UserService;
import com.dyks.aihubbe.global.response.GlobalResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/myinfo")
	@Operation(summary = "본인 프로필 조회", description = "본인의 프로필 내용을 조회합니다.")
	public ResponseEntity<GlobalResponseDto<UserProfileResponseDto>> getProfile(HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(GlobalResponseDto.success(userService.getProfile(request)));
	}

	@PatchMapping("/myinfo")
	@Operation(summary = "본인 프로필 수정", description = "본인의 프로필을 수정합니다.")
	public ResponseEntity<GlobalResponseDto<String>> updateProfile(
		@RequestBody UserProfileRequestDto userProfileRequestDto, HttpServletRequest request) {
		userService.updateProfile(request, userProfileRequestDto);
		return ResponseEntity.status(HttpStatus.OK).body(GlobalResponseDto.success());
	}

}
