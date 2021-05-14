package com.fans.bravegirls.batch.exception.http;


import com.fans.bravegirls.batch.biz.common.ErrorCd;
import com.fans.bravegirls.batch.exception.ExceptionBase;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.FORBIDDEN, reason="Reqeust Forbidden")  // 403
public class ForbiddenException extends ExceptionBase {

	/*  */
	private static final long serialVersionUID = 4382922857122470846L;

	public ForbiddenException() {
		super("error.403");
	}
	public ForbiddenException(String errorCode,String... args) {
		super(errorCode,args);
	}
	public ForbiddenException(ErrorCd errorCd) {
		super(errorCd.name());
	}
	public ForbiddenException(String errorCode) {
	    super(errorCode);
	}

}
