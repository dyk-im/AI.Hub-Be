package com.dyks.aihubbe.domain.user.exception;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class UserNotFoundException extends CommonException {
	public UserNotFoundException() {
		super(ErrorCode.USER_NOT_FOUND);
	}
}
