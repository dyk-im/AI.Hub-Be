package com.dyks.aihubbe.global.error.exception.custom;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class RejectDuplicationException extends CommonException {
	public RejectDuplicationException() {

		super(ErrorCode.REJECT_DUPLICATION);
	}
}
