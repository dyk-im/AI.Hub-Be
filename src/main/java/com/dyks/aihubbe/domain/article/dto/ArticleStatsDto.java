package com.dyks.aihubbe.domain.article.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleStatsDto {
	private long likeCnt;
	private long commentCnt;

	@Builder
	private ArticleStatsDto(long likeCnt, long commentCnt) {
		this.likeCnt = likeCnt;
		this.commentCnt = commentCnt;
	}

	public static ArticleStatsDto from(long likeCnt, long commentCnt) {
		return ArticleStatsDto.builder()
			.likeCnt(likeCnt)
			.commentCnt(commentCnt)
			.build();
	}
}
