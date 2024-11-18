package com.dyks.aihubbe.global.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GlobalResponseDto<T> {

	private boolean isSuccess;
	private int code;
	private LocalDateTime timestamp;
	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	@Builder
	public GlobalResponseDto(boolean isSuccess, int code, LocalDateTime timestamp, String message, T result) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.timestamp = timestamp;
		this.message = message;
		this.result = result;
	}

	public static <T> GlobalResponseDto<T> success(T result, int code) {
		return GlobalResponseDto.<T>builder()
			.isSuccess(true)
			.code(code)
			.timestamp(LocalDateTime.now())
			.message("success")
			.result(result)
			.build();
	}

	public static <T> GlobalResponseDto<T> success(T result) {
		return GlobalResponseDto.<T>builder()
			.isSuccess(true)
			.code(200)
			.timestamp(LocalDateTime.now())
			.message("success")
			.result(result)
			.build();
	}

	public static <T> GlobalResponseDto<T> success() {
		return GlobalResponseDto.<T>builder()
			.isSuccess(true)
			.code(200)
			.timestamp(LocalDateTime.now())
			.message("success")
			.result(null)
			.build();
	}

	public static <T> GlobalResponseDto<T> fail(ErrorCode code, String message) {
		return GlobalResponseDto.<T>builder()
			.isSuccess(false)
			.code(code)
			.timestamp(LocalDateTime.now())
			.message(message)
			.result(null)
			.build();
	}
}
