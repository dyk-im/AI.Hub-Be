package com.dyks.aihubbe.domain.user.exception;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class InvalidFileFormat extends CommonException {
	public InvalidFileFormat() {
		super(ErrorCode.INVALID_FILE_FORMAT);
	}
}
