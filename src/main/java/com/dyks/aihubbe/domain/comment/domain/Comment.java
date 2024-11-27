package com.dyks.aihubbe.domain.comment.domain;

import java.util.UUID;

import com.dyks.aihubbe.domain.article.domain.Article;
import com.dyks.aihubbe.domain.shared.BaseTimeEntity;
import com.dyks.aihubbe.domain.shared.DeletedStatus;
import com.dyks.aihubbe.domain.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID commentId;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private User writer;

	@NotNull
	@Column
	private String content;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	private Article article;

	@NotNull
	@Column(columnDefinition = "VARCHAR(30)")
	@Enumerated(EnumType.STRING)
	private DeletedStatus deletedStatus;

	@Column
	private int viewCnt;

	@Builder
	private Comment(User writer, String content, Article article) {
		this.writer = writer;
		this.content = content;
		this.article = article;
		this.deletedStatus = DeletedStatus.NOT_DELETED;
	}

	public static Comment createComment(User writer, String content, Article article) {
		return Comment.builder()
			.writer(writer)
			.content(content)
			.article(article)
			.build();
	}

	public void deleteComment() {
		this.deletedStatus = DeletedStatus.DELETED;
	}

	public void  updateViewCount() {
		this.viewCnt++;
	}
}
