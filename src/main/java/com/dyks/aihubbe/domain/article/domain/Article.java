package com.dyks.aihubbe.domain.article.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dyks.aihubbe.domain.articleLike.domain.ArticleLike;
import com.dyks.aihubbe.domain.comment.domain.Comment;
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
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID articleId;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private User writer;

	@NotNull
	@Column
	private String title;

	@NotNull
	@Column
	private String content;

	@Column
	private int viewCnt;

	@Column
	private String category;

	@NotNull
	@Column(columnDefinition = "VARCHAR(30)")
	@Enumerated(EnumType.STRING)
	private DeletedStatus deletedStatus;

	@OneToMany(mappedBy = "article")
	private List<Comment> comments;

	@OneToMany(mappedBy = "article")
	private List<ArticleLike> articleLikes;

	@Builder
	private Article(User writer, String title, String content, String category) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.viewCnt = 0;
		this.category = category;
		this.deletedStatus = DeletedStatus.NOT_DELETED;
	}

	public static Article createArticle(User writer, String title, String content, String category) {
		return Article.builder()
			.writer(writer)
			.title(title)
			.content(content)
			.category(category)
			.build();
	}

	public void deleteArticle() {
		this.deletedStatus = DeletedStatus.DELETED;
	}

	public void updateViewCount() {
		this.viewCnt++;
	}

	public void addComments(List<Comment> comments) {
		if(this.comments == null){
			this.comments = new ArrayList<>();
		}
		this.comments.addAll(comments);
	}

	public void addArticleLike(List<ArticleLike> articleLikes) {
		if(this.articleLikes == null){
			this.articleLikes = new ArrayList<>();
		}
		this.articleLikes.addAll(articleLikes);
	}
	
}
