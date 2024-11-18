package com.dyks.aihubbe.global.error.exception.custom;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class InternalServerErrorException extends CommonException {
	public InternalServerErrorException() {
		super(ErrorCode.INTERNAL_SERVER_ERROR);
	}
}
