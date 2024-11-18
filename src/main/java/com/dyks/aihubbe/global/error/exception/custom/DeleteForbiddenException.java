package com.dyks.aihubbe.global.error.exception.custom;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class DeleteForbiddenException extends CommonException {
	public DeleteForbiddenException() {

		super(ErrorCode.DELETE_FORBIDDEN);
	}
}