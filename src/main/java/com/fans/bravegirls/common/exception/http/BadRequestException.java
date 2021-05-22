package com.fans.bravegirls.common.exception.http;

import com.fans.bravegirls.common.ErrorCd;
import com.fans.bravegirls.common.exception.ExceptionBase;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Bad Reqeust")  // 400
public class BadRequestException extends ExceptionBase {

	/*  */
	private static final long serialVersionUID = -8332173558308935874L;



	public BadRequestException() {
		super("error.CO400");
	}

	public BadRequestException(ErrorCd errorCd) {
		super(errorCd.name());
	}
	public BadRequestException(String errorCode) {
	    super(errorCode);
	}
	public BadRequestException(String errorCode,String... args) {
		super(errorCode,args);
	}

}
