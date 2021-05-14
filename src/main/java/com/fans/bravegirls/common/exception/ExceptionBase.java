package com.fans.bravegirls.common.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fans.bravegirls.common.ErrorCd;

public class ExceptionBase extends RuntimeException {

    Logger L = LoggerFactory.getLogger(this.getClass());

    String[] args;

    public ExceptionBase(String message) {
        super(message);
    }
    public ExceptionBase(String message,String[] args) {
        super(message);
        this.args = args;
    }

    public ExceptionBase(ErrorCd errorCd) {
        super(errorCd.name());
    }

    public ExceptionBase(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionBase(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
