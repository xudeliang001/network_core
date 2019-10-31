/***********************************************************************
 * ErrorCode.java H3C所有，受到法律的保护，任何公司或个人，未经授权不得擅自拷贝。
 *
 * @copyright Copyright: 2015-2020
 * @creator likewei<li.kewei@h3c.com>
 * @create-time 2018/4/25 11:34
 * @revision $Id: *
 ***********************************************************************/
package com.uca.network.common.exception;

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
