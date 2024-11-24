package com.dyks.aihubbe.domain.articleLike.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dyks.aihubbe.domain.articleLike.domain.ArticleLike;
import com.dyks.aihubbe.domain.shared.DeletedStatus;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, UUID> {
	//Optional<ArticleLike> findByArticle_ArticleIdAndUser_UserId(UUID articleId, Long userId);

	@Query("SELECT COUNT(l) FROM ArticleLike l WHERE l.article.articleId = :articleId AND l.deletedStatus = com.dyks.aihubbe.domain.shared.DeletedStatus.NOT_DELETED")
	long countLikesByArticleId(@Param("articleId") UUID articleId);

	Optional<ArticleLike> findByArticle_ArticleIdAndUser_UserIdAndDeletedStatus(UUID articleId, Long userId, DeletedStatus status);

	Boolean existsByArticle_ArticleIdAndUser_UserIdAndDeletedStatus(UUID articleId, Long userId, DeletedStatus status);
}
