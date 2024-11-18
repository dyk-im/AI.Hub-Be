package com.dyks.aihubbe.global.error.exception;

import com.dyks.aihubbe.global.error.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonException extends RuntimeException {
	private final ErrorCode errorCode;
}
