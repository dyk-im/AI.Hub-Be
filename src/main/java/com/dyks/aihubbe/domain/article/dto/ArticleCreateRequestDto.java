package com.dyks.aihubbe.domain.article.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleCreateRequestDto {
	private String title;
	private String content;
	private String category;
}
