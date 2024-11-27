package com.dyks.aihubbe.global.auth.logout;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dyks.aihubbe.global.auth.jwt.JwtService;
import com.dyks.aihubbe.global.response.GlobalResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/logout")
@RequiredArgsConstructor
public class LogoutController {

	private final LogoutService logoutService;
	private final JwtService jwtService;

	@PostMapping
	public ResponseEntity<String> logout(HttpServletRequest request) {
		// 헤더에서 AccessToken 추출
		String accessToken = jwtService.extractAccessToken(request).orElse(null);

		if (accessToken != null && jwtService.isTokenValid(accessToken)) {
			logoutService.addTokenToBlackList(accessToken);
			return ResponseEntity.ok("Successfully logged out.");
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token.");
	}
}
