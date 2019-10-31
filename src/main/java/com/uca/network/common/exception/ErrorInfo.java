/***********************************************************************
 * ErrorInfo.java H3C所有，受到法律的保护，任何公司或个人，未经授权不得擅自拷贝。
 *
 * @copyright Copyright: 2015-2020
 * @creator <likewei><li.kewei@h3c.com>
 * @create-time 2018/6/13 11:27
 * @revision $Id: *
 ***********************************************************************/
package com.uca.network.common.exception;

public class ErrorInfo {
    private ErrorCode code;
    private String field;
    private String message;

    public ErrorInfo(ErrorCode code, String field, String message) {

        this.code = code;
        this.field = field;
        this.message = message;
    }

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
