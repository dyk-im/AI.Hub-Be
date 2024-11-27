package com.dyks.aihubbe.domain.article.exception;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class ArticleNotFoundException extends CommonException {
	public ArticleNotFoundException() {

		super(ErrorCode.ARTICLE_NOT_FOUND);
	}
}
