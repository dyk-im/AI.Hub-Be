package com.dyks.aihubbe.global.auth.logout;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LogoutService {

	private final Set<String> tokenBlacklist = new HashSet<>();

	public void addTokenToBlackList(String token) {
		tokenBlacklist.add(token);
	}

	public boolean isTokenBlacklisted(String token) {
		return tokenBlacklist.contains(token);
	}
}
