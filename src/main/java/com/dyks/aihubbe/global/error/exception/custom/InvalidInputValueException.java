package com.dyks.aihubbe.global.error.exception.custom;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class InvalidInputValueException extends CommonException {
	public InvalidInputValueException() {

		super(ErrorCode.INVALID_INPUT_VALUE);
	}
}
