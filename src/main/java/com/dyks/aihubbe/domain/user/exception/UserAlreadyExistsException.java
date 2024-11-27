package com.dyks.aihubbe.domain.user.exception;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class UserAlreadyExistsException extends CommonException {
	public UserAlreadyExistsException() {
		super(ErrorCode.USER_ALREADY_EXISTS);
	}
}
