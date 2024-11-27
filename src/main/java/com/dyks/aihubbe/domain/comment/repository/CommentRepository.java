package com.dyks.aihubbe.domain.comment.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dyks.aihubbe.domain.article.domain.Article;
import com.dyks.aihubbe.domain.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

	@Query("SELECT c FROM Comment c WHERE c.article = :article AND c.deletedStatus = com.dyks.aihubbe.domain.shared.DeletedStatus.NOT_DELETED")
	List<Comment> findAllByArticleAndNotDeleted(@Param("article") Article article);

	@Query("SELECT COUNT(c) FROM Comment c WHERE c.article = :article AND c.deletedStatus = com.dyks.aihubbe.domain.shared.DeletedStatus.NOT_DELETED")
	long countCommentsByArticle(@Param("article") Article article);
}
