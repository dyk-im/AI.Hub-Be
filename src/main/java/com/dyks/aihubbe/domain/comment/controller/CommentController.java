package com.dyks.aihubbe.domain.comment.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dyks.aihubbe.domain.comment.dto.CommentCreateRequestDto;
import com.dyks.aihubbe.domain.comment.service.CommentService;
import com.dyks.aihubbe.global.response.GlobalResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	//댓글 작성
	@PostMapping("/{articleId}")
	@Operation(summary = "댓글 작성", description = "새 댓글을 작성합니다.")
	public ResponseEntity<GlobalResponseDto<Void>> createComment(
		HttpServletRequest request,
		@RequestBody CommentCreateRequestDto commentCreateRequestDto,
		@PathVariable UUID articleId){
		commentService.createComment(request, commentCreateRequestDto, articleId);
		return ResponseEntity.status(HttpStatus.OK).body(GlobalResponseDto.success());
	}

	//댓글 삭제(소프트)
	@PatchMapping("/{commentId}")
	@Operation(summary = "댓글 삭제", description = "댓글 삭제(상태 변경)합니다.")
	public ResponseEntity<GlobalResponseDto<Void>> deleteComment(
		HttpServletRequest request,
		@PathVariable UUID commentId){
		commentService.deleteComment(request, commentId);
		return ResponseEntity.status(HttpStatus.OK).body(GlobalResponseDto.success());
	}
}
