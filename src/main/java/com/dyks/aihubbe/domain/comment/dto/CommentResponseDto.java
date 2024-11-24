package com.dyks.aihubbe.domain.comment.dto;

import java.util.Collections;
import java.util.UUID;

import com.dyks.aihubbe.domain.comment.domain.Comment;
import com.dyks.aihubbe.domain.shared.DeletedStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
	private UUID commentId;
	private String customId;
	private String userName;
	private String content;
	private DeletedStatus deletedStatus;
	private boolean owner;

	@Builder
	private CommentResponseDto(UUID commentId, String userName, String customId, String content, DeletedStatus deletedStatus,
		int viewCnt, long likeCnt, boolean owner) {
		this.commentId = commentId;
		this.userName = userName;
		this.customId = customId;
		this.content = content;
		this.deletedStatus = deletedStatus;
		this.owner = owner;
	}

	public static CommentResponseDto from(Comment comment, boolean isOwner) {
		return CommentResponseDto.builder()
			.commentId(comment.getCommentId())
			.userName(comment.getWriter().getUserName())
			.customId(comment.getWriter().getCustomId())
			.content(comment.getContent())
			.deletedStatus(comment.getDeletedStatus())
			.owner(isOwner)
			.build();
	}
}

