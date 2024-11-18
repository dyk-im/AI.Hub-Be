package com.dyks.aihubbe.global.error.exception.custom;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class InvalidTokenException extends CommonException {
	public InvalidTokenException() {

		super(ErrorCode.INVALID_TOKEN);
	}
}
