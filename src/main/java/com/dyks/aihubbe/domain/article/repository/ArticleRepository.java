package com.dyks.aihubbe.domain.article.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dyks.aihubbe.domain.article.domain.Article;
import com.dyks.aihubbe.domain.user.domain.User;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

	List<Article> findByWriter(User writer);

	@Query("SELECT a FROM Article a WHERE a.deletedStatus = com.dyks.aihubbe.domain.shared.DeletedStatus.NOT_DELETED ORDER BY a.createdAt DESC")
	List<Article> findRecentArticles(Pageable pageable);

	@Query("SELECT a FROM Article a WHERE a.createdAt < :cursor AND a.deletedStatus = com.dyks.aihubbe.domain.shared.DeletedStatus.NOT_DELETED ORDER BY a.createdAt DESC")
	List<Article> findRecentArticles(@Param("cursor") LocalDateTime cursor, Pageable pageable);
}
