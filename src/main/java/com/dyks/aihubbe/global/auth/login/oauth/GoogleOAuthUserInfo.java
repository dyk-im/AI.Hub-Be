package com.dyks.aihubbe.global.auth.login.oauth;

import java.util.Map;

public class GoogleOAuthUserInfo {

	private final Map<String, Object> attributes;

	public GoogleOAuthUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	//구글은 프로바이더 x => reponse x
	public String getEmail() {
		return (String)attributes.get("email");
	}
}