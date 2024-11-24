package com.dyks.aihubbe.global.auth.login.oauth.handler;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.response.GlobalResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuthLoginFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		ErrorCode errorCode =
			exception instanceof BadCredentialsException ? ErrorCode.LOGIN_FAIL : ErrorCode.USER_NOT_FOUND;
		GlobalResponseDto<String> responseDto = GlobalResponseDto.fail(ErrorCode.valueOf(errorCode.getCode()),
			errorCode.getMessage());

		response.setStatus(errorCode.getStatus());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		log.info("소셜 로그인에 실패했습니다. 에러 메시지 : {}", exception.getMessage());
	}
}
