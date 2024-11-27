package com.dyks.aihubbe.domain.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dyks.aihubbe.domain.article.domain.Article;
import com.dyks.aihubbe.domain.article.exception.ArticleNotFoundException;
import com.dyks.aihubbe.domain.article.repository.ArticleRepository;
import com.dyks.aihubbe.domain.comment.domain.Comment;
import com.dyks.aihubbe.domain.comment.dto.CommentCreateRequestDto;
import com.dyks.aihubbe.domain.comment.exception.CommentNotFoundException;
import com.dyks.aihubbe.domain.comment.repository.CommentRepository;
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
public class CommentService {

	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final CommentRepository commentRepository;

	//댓글 작성
	public void createComment(HttpServletRequest request,
		CommentCreateRequestDto commentCreateRequestDto, UUID articleId) {

		List<Comment> commentList = new ArrayList<>();
		User writer = getUser(request);
		Article targetArticle = articleRepository.findById(articleId)
			.orElseThrow(ArticleNotFoundException::new);
		String content = commentCreateRequestDto.getContent();

		Comment newComment = Comment.createComment(writer, content, targetArticle);
		commentList.add(newComment);
		targetArticle.addComments(commentList);
		commentRepository.save(newComment);
	}

	//댓글 삭제 (상태 변경)
	public void deleteComment(HttpServletRequest request, UUID commentId) {
		User user = getUser(request);

		Comment targetComment = commentRepository.findById(commentId)
			.orElseThrow(CommentNotFoundException::new);

		if(!(targetComment.getWriter().getUserId().equals(user.getUserId()))){
			throw new DeleteForbiddenException();
		}
		targetComment.deleteComment();
		commentRepository.save(targetComment);
	}

	private User getUser(HttpServletRequest request) {
		User user = jwtService.extractAccessToken(request)
			.filter(jwtService::isTokenValid)
			.flatMap(jwtService::extractEmail)
			.flatMap(userRepository::findByEmail)
			.orElseThrow(UserNotFoundException::new);

		return user;
	}
}

