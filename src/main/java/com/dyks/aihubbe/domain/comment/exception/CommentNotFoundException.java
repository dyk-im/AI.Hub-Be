package com.dyks.aihubbe.domain.comment.exception;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class CommentNotFoundException extends CommonException {
	public CommentNotFoundException() {
		super(ErrorCode.COMMENT_NOT_FOUND);
	}
}
