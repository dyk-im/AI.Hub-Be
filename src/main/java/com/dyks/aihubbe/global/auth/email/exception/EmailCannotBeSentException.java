package com.dyks.aihubbe.global.auth.email.exception;

import com.dyks.aihubbe.global.error.ErrorCode;
import com.dyks.aihubbe.global.error.exception.CommonException;

public class EmailCannotBeSentException extends CommonException {
  public EmailCannotBeSentException() {
    super(ErrorCode.EMAIL_CANNOT_BE_SENT);
  }
}
