package com.dyks.aihubbe.domain.articleLike.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dyks.aihubbe.domain.article.domain.Article;
import com.dyks.aihubbe.domain.article.exception.ArticleNotFoundException;
import com.dyks.aihubbe.domain.article.repository.ArticleRepository;
import com.dyks.aihubbe.domain.articleLike.domain.ArticleLike;
import com.dyks.aihubbe.domain.articleLike.domain.repository.ArticleLikeRepository;
import com.dyks.aihubbe.domain.articleLike.dto.ArticleLikeResponseDto;
import com.dyks.aihubbe.domain.articleLike.exception.ArticleLikeNotFoundException;
import com.dyks.aihubbe.domain.shared.DeletedStatus;
import com.dyks.aihubbe.domain.user.domain.User;
import com.dyks.aihubbe.domain.user.exception.UserNotFoundException;
import com.dyks.aihubbe.domain.user.repository.UserRepository;
import com.dyks.aihubbe.global.auth.jwt.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleLikeService {
	private final ArticleLikeRepository articleLikeRepository;
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;
	private final JwtService jwtService;

	public ArticleLikeResponseDto likeArticle(HttpServletRequest request, UUID articleId) {
		User user = getUser(request);
		List<ArticleLike> articleLikeList = new ArrayList<>();
		Article article = articleRepository.findById(articleId)
			.orElseThrow(ArticleNotFoundException::new);

		Optional<ArticleLike> existingLike = articleLikeRepository.findByArticle_ArticleIdAndUser_UserIdAndDeletedStatus(
			articleId, user.getUserId(), DeletedStatus.NOT_DELETED);

		if (existingLike.isPresent()) {
			return ArticleLikeResponseDto.from(existingLike.get());
		}

		ArticleLike articleLike = ArticleLike.create(article, user);
		articleLikeList.add(articleLike);
		article.addArticleLike(articleLikeList);

		return ArticleLikeResponseDto.from(articleLikeRepository.save(articleLike));
	}

	public void unLike(HttpServletRequest request, UUID articleId) {
		User user = getUser(request);
		ArticleLike articleLike = articleLikeRepository.findByArticle_ArticleIdAndUser_UserIdAndDeletedStatus(
				articleId, user.getUserId(), DeletedStatus.NOT_DELETED)
			.orElseThrow(ArticleLikeNotFoundException::new);
		articleLike.deleteArticleLike();
		articleLikeRepository.save(articleLike);
	}

	//JWT 토큰 기반 사용자 정보 반환 메소드
	private User getUser(HttpServletRequest request) {
		User user = jwtService.extractAccessToken(request)
			.filter(jwtService::isTokenValid)
			.flatMap(jwtService::extractEmail)
			.flatMap(userRepository::findByEmail)
			.orElseThrow(UserNotFoundException::new);

		return user;
	}
}
