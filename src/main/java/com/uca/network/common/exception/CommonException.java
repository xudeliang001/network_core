/***********************************************************************
 * CommonException.java H3C所有，受到法律的保护，任何公司或个人，未经授权不得擅自拷贝。
 *
 * @copyright Copyright: 2015-2020
 * @creator likewei<li.kewei@h3c.com>
 * @create-time 2018/4/25 11:33
 * @revision $Id: *
 ***********************************************************************/
package com.uca.network.common.exception;

public class CommonException extends RuntimeException {
    private final ErrorCode code;
    private final String mes;
    private final String field;

    public CommonException(ErrorCode code, String field, String message) {
        super(code.toString() + " - " + message);
        this.code = code;
        this.field = field;
        this.mes = message;
    }

    public CommonException(String field, String message) {
        super(ErrorCode.FIELD_ERROR.toString() + " - " + message);
        this.code = ErrorCode.FIELD_ERROR;
        this.field = field;
        this.mes = message;
    }

    public CommonException(ErrorInfo errorInfo) {
        this.code = errorInfo.getCode();
        this.mes = errorInfo.getMessage();
        this.field = errorInfo.getField();
    }

    public ErrorCode getCode() {
        return code;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return mes;
    }
}
