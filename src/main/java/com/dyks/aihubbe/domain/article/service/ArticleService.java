package com.dyks.aihubbe.domain.article.service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dyks.aihubbe.domain.article.domain.Article;
import com.dyks.aihubbe.domain.article.dto.ArticleCreateRequestDto;
import com.dyks.aihubbe.domain.article.dto.ArticleCreateResponseDto;
import com.dyks.aihubbe.domain.article.dto.ArticleResponseDto;
import com.dyks.aihubbe.domain.article.dto.ArticleStatsDto;
import com.dyks.aihubbe.domain.article.dto.ArticlesResponseDto;
import com.dyks.aihubbe.domain.article.exception.ArticleNotFoundException;
import com.dyks.aihubbe.domain.article.repository.ArticleRepository;
import com.dyks.aihubbe.domain.articleLike.domain.repository.ArticleLikeRepository;
import com.dyks.aihubbe.domain.comment.domain.Comment;
import com.dyks.aihubbe.domain.comment.repository.CommentRepository;
import com.dyks.aihubbe.domain.shared.DeletedStatus;
import com.dyks.aihubbe.domain.user.domain.User;
import com.dyks.aihubbe.domain.user.exception.UserNotFoundException;
import com.dyks.aihubbe.domain.user.repository.UserRepository;
import com.dyks.aihubbe.global.auth.jwt.JwtService;
import com.dyks.aihubbe.global.error.exception.custom.DeleteForbiddenException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final ArticleLikeRepository articleLikeRepository;
	private final JwtService jwtService;

	// 자기 게시물 가져오기
	public List<ArticlesResponseDto> getArticles(String customId) {

		User user = getUser(customId);
		List<Article> articles = articleRepository.findByWriter(user);

		return articles.stream()
			.map(ArticlesResponseDto::of)
			.collect(Collectors.toList());
	}

	// 유저 정보 조회
	private User getUser(String customId) {
		return userRepository.findByCustomId(customId).orElseThrow(UserNotFoundException::new);
	}

	//게시글 작성
	public ArticleCreateResponseDto createArticle(HttpServletRequest request,
		ArticleCreateRequestDto articleCreateRequestDto) {
		User writer = getUser(request);
		String title = articleCreateRequestDto.getTitle();
		String content = articleCreateRequestDto.getContent();
		String category = articleCreateRequestDto.getCategory();
		//게시글 생성
		Article newArticle = Article.createArticle(writer, title, content, category);

		return ArticleCreateResponseDto.from(articleRepository.save(newArticle));
	}

	//게시글 삭제 (상태 변경)
	public void deleteArticle(HttpServletRequest request, UUID articleId) {
		User user = getUser(request);

		Article targetArticle = articleRepository.findById(articleId)
			.orElseThrow(ArticleNotFoundException::new);

		if (!(targetArticle.getWriter().getUserId().equals(user.getUserId()))) {
			throw new DeleteForbiddenException();
		} else {
			targetArticle.deleteArticle();
		}
	}

	//게시글 단건 조회
	public ArticleResponseDto getArticle(HttpServletRequest request, UUID articleId) {

		User user = getUser(request);

		Article targetArticle = articleRepository.findById(articleId)
			.orElseThrow(ArticleNotFoundException::new);
		ArticleStatsDto stats = findArticleStats(targetArticle);
		targetArticle.updateViewCount();
		boolean isOwner = targetArticle.getWriter().getUserId().equals(user.getUserId());
		boolean isLiked = articleLikeRepository.existsByArticle_ArticleIdAndUser_UserIdAndDeletedStatus(
			targetArticle.getArticleId(), user.getUserId(), DeletedStatus.NOT_DELETED);

		List<Comment> comments = commentRepository.findAllByArticleAndNotDeleted(targetArticle);

		return ArticleResponseDto.from(targetArticle, isOwner, isLiked, stats);
	}

	//게시글 전체 조회
	public List<ArticleResponseDto> getArticles(HttpServletRequest request, LocalDateTime cursor, int size) {

		User user = getUser(request);

		Pageable pageable = PageRequest.of(0, size);

		List<Article> articles = (cursor == null) ?
			articleRepository.findRecentArticles(pageable) : // 처음 로드 시
			articleRepository.findRecentArticles(cursor, pageable);

		List<Comment> comments = articles.stream()
			.flatMap(article -> commentRepository.findAllByArticleAndNotDeleted(article).stream())
			.collect(Collectors.toList());

		return articles
			.stream()
			.map(article -> {
				boolean isOwner = article.getWriter().getUserId().equals(user.getUserId());
				ArticleStatsDto stats = findArticleStats(article);
				boolean isLiked = articleLikeRepository.existsByArticle_ArticleIdAndUser_UserIdAndDeletedStatus(
					article.getArticleId(), user.getUserId(), DeletedStatus.NOT_DELETED);
				return ArticleResponseDto.from(article, isOwner, isLiked, stats);
			})
			.collect(Collectors.toList());
	}

	public ArticleStatsDto findArticleStats(Article article) {
		long likeCount = articleLikeRepository.countLikesByArticleId(article.getArticleId());
		long commentCount = commentRepository.countCommentsByArticle(article);
		return ArticleStatsDto.from(likeCount, commentCount);
	}

	//JWT 토큰 기반 사용자 정보 반환 메소드
	private User getUser(HttpServletRequest request) {

		return jwtService.extractAccessToken(request)
			.filter(jwtService::isTokenValid)
			.flatMap(jwtService::extractEmail)
			.flatMap(userRepository::findByEmail)
			.orElseThrow(UserNotFoundException::new);
	}
}
