package com.dyks.aihubbe.domain.articleLike.exception;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class ArticleLikeNotFoundException extends CommonException {
	public ArticleLikeNotFoundException() {
		super(ErrorCode.ARTICLE_LIKE_NOT_FOUND);
	}
}
