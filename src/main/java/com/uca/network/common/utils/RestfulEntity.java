package com.uca.network.common.utils;

import io.swagger.annotations.ApiModelProperty;

public class RestfulEntity<T> {
    /**
     * 接口调用返回状态，TRUE为正常返回，FALSE为异常返回
     */
    @ApiModelProperty(value = "返回状态", required = true, notes = "true or false")
    private Boolean status;
    /**
     * 用户认证状态，TRUE为已认证，FALSE为未认证
     */
    @ApiModelProperty(value = "用户是否认证", required = true, notes = "true or false")
    private Boolean auth;
    /**
     * 接口调用返回编码，暂未使用，默认为0(正常)，
     * 当调用出错时,默认为1(异常)，可自行填写对应错误码
     */
    @ApiModelProperty(value = "接口调用返回码", required = true)
    private String code;
    /**
     * 接口调用返回数据
     */
    @ApiModelProperty(value = "返回数据", required = true)
    private T res;

    /**
     * 调用成功或者失败都可以设置消息
     */
    @ApiModelProperty(value = "接口调用信息", required = true)
    private String msg;

    public RestfulEntity() {

    }


    public RestfulEntity(Boolean status, Boolean auth, String code, T res) {
        this.status = status;
        this.auth = auth;
        this.code = code;
        this.res = res;
    }

    public RestfulEntity(Boolean status, Boolean auth, String code, T res, String msg) {
        this.status = status;
        this.auth = auth;
        this.code = code;
        this.res = res;
        this.msg = msg;
    }


    /**
     * 返回异常请求时调用，须重新登录
     *
     * @param res
     * @return
     */
    public static <T> RestfulEntity<T> getAuthFailure(T res) {
        return new RestfulEntity<>(false, false, "1", res);
    }

    /**
     * 返回异常请求时调用，无须重新登录
     *
     * @param res
     * @return
     */
    public static <T> RestfulEntity<T> getFailure(T res) {
        return new RestfulEntity<>(false, true, "1", res);
    }

    /**
     * 返回异常请求时调用，无须重新登录
     * 自定义失败消息
     *
     * @param res
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> RestfulEntity<T> getFailure(T res, String msg) {
        return new RestfulEntity<>(false, true, "1", res, msg);
    }

    public static <T> RestfulEntity<T> getFailure(T res, String msg, String code) {
        return new RestfulEntity<>(false, true, code, res, msg);
    }


    /**
     * 返回正常请求时调用
     *
     * @param res
     * @return
     */
    public static <T> RestfulEntity<T> getSuccess(T res) {
        return new RestfulEntity<>(true, true, "0", res);
    }

    /**
     * 返回正常请求时调用
     * 自定义成功消息
     *
     * @param res
     * @param <T>
     * @return
     */
    public static <T> RestfulEntity<T> getSuccess(T res, String msg) {
        return new RestfulEntity<>(true, true, "0", res, msg);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getRes() {
        return res;
    }

    public void setRes(T res) {
        this.res = res;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
