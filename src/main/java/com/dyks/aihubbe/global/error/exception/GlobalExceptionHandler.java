package com.dyks.aihubbe.global.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.response.GlobalResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static void showErrorLog(ErrorCode errorCode){
		log.error("errorCode: {}, message{}", errorCode.getCode(), errorCode.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<GlobalResponseDto<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(GlobalResponseDto.fail(400, ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<GlobalResponseDto> handleGeneralException(Exception ex){
		ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.valueOf(errorCode.getStatus()))
			.body(GlobalResponseDto.fail(errorCode, errorCode.getMessage()));
	}

	@ExceptionHandler(CommonException.class)
	public ResponseEntity<GlobalResponseDto<String>> handleGeneralException(CommonException ex){
		ErrorCode errorCode = ex.getErrorCode();
		showErrorLog(errorCode);
		return ResponseEntity.status(HttpStatus.valueOf(errorCode.getStatus()))
			.body(GlobalResponseDto.fail(errorCode, errorCode.getMessage()));
	}
}
