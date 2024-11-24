package com.dyks.aihubbe.global.auth.login.oauth;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.dyks.aihubbe.domain.user.domain.Role;

import lombok.Getter;

@Getter
public class CustomOAuthUser extends DefaultOAuth2User {

	private String email;
	private Role role;

	public CustomOAuthUser(Collection<? extends GrantedAuthority> authorities,
		Map<String, Object> attributes, String nameAttributeKey,
		String email, Role role) {
		super(authorities, attributes, nameAttributeKey);
		this.email = email;
		this.role = role;
	}
}
