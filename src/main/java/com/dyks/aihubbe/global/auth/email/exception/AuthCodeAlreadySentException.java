package com.dyks.aihubbe.global.auth.email.exception;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class AuthCodeAlreadySentException extends CommonException {
	public AuthCodeAlreadySentException() {
		super(ErrorCode.AUTH_CODE_ALREADY_SENT);
	}
}
