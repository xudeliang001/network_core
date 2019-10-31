package com.uca.network.common.exception;

/**
 * @author admin
 */

public enum ErrorCode {
    FIELD_ERROR(1000),
    NAME_INVALID(1002, "输入参数不符合格式要求");


    private final Integer val;
    private String message;
    private String[] params;

    ErrorCode(Integer val) {
        this.val = val;
    }

    ErrorCode(Integer val, String message) {
        this.val = val;
        this.message = message;
    }

    public ErrorCode withParams(String... params) {
        this.params = params;
        return this;
    }

    public Integer getVal() {
        return val;
    }
    public String getMessage() {
        if (params != null) {
            return String.format(message, params);
        }
        return message;
    }
}
