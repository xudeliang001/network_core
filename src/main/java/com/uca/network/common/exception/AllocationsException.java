package com.uca.network.common.exception;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/4 14:01
 */
public class AllocationsException extends RuntimeException {

    private ErrorCode errorCode;

    public AllocationsException(ErrorCode errorCode, String... args) {
        super(String.format(errorCode.getMessage(), args));
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
