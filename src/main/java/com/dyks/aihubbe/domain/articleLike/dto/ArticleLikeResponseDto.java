package com.dyks.aihubbe.domain.articleLike.dto;

import java.util.UUID;

import com.dyks.aihubbe.domain.articleLike.domain.ArticleLike;
import com.dyks.aihubbe.domain.shared.DeletedStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleLikeResponseDto {
	private UUID articleId;
	private DeletedStatus status;

	@Builder
	private ArticleLikeResponseDto(UUID articleId, DeletedStatus status) {
		this.articleId = articleId;
		this.status = status;
	}

	public static ArticleLikeResponseDto from(ArticleLike articleLike) {
		return ArticleLikeResponseDto.builder()
			.articleId(articleLike.getArticle().getArticleId())
			.status(articleLike.getDeletedStatus())
			.build();
	}
}
