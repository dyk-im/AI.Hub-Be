package com.dyks.aihubbe.domain.article.dto;

import com.dyks.aihubbe.domain.article.domain.Article;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArticlesResponseDto {
	private String writer;
	private String content;

	@Builder
	private ArticlesResponseDto(String writer, String content) {
		this.writer = writer;
		this.content = content;
	}

	public static ArticlesResponseDto of(Article article) {
		return ArticlesResponseDto.builder()
			.writer(article.getWriter().getUserName())
			.content(article.getContent())
			.build();
	}
}
