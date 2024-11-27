package com.dyks.aihubbe.domain.article.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.dyks.aihubbe.domain.article.domain.Article;
import com.dyks.aihubbe.domain.shared.DeletedStatus;
import com.dyks.aihubbe.domain.comment.domain.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {
	private UUID articleId;
	private String customId;
	private String userName;
	private String title;
	private String content;
	private String category;
	private DeletedStatus deletedStatus;
	private List<String> comments; // 댓글 내용을 문자열 리스트로 변경
	private int viewCnt;
	private long likeCnt;
	private long commentCnt;
	private boolean owner;
	private boolean isLiked;
	private LocalDateTime createdAt;

	@Builder
	private ArticleResponseDto(UUID articleId, String userName, String customId, String title, String content,
		DeletedStatus deletedStatus, List<String> comments, String category,
		int viewCnt, long likeCnt, long commentCnt, boolean owner, boolean isLiked, LocalDateTime createdAt) {

		this.articleId = articleId;
		this.userName = userName;
		this.customId = customId;
		this.title = title;
		this.content = content;
		this.deletedStatus = deletedStatus;
		this.category = category;
		this.viewCnt = viewCnt;
		this.likeCnt = likeCnt;
		this.commentCnt = commentCnt;
		this.owner = owner;
		this.comments = comments;
		this.isLiked = isLiked;
		this.createdAt = createdAt;
	}

	public static ArticleResponseDto from(Article article, boolean isOwner, boolean isLiked, ArticleStatsDto stats) {
		return ArticleResponseDto.builder()
			.articleId(article.getArticleId())
			.content(article.getContent())
			.deletedStatus(article.getDeletedStatus())
			.userName(article.getWriter().getUserName())
			.customId(article.getWriter().getCustomId())
			.title(article.getTitle())
			.category(article.getCategory())
			.comments(article.getComments() != null ? article.getComments()
				.stream()
				.filter(comment -> comment != null
					&& comment.getDeletedStatus() == DeletedStatus.NOT_DELETED) // null 및 삭제된 댓글 필터링
				.map(Comment::getContent) // 댓글 내용을 문자열로 변환
				.collect(Collectors.toList()) : null)
			.viewCnt(article.getViewCnt())
			.likeCnt(stats.getLikeCnt())
			.commentCnt(stats.getCommentCnt())
			.owner(isOwner)
			.isLiked(isLiked)
			.createdAt(article.getCreatedAt())
			.build();
	}
}
