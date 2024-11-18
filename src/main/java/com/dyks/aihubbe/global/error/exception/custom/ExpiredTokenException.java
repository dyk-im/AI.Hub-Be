package com.dyks.aihubbe.global.error.exception.custom;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class ExpiredTokenException extends CommonException {
	public ExpiredTokenException() {

		super(ErrorCode.EXPIRED_TOKEN);
	}
}
