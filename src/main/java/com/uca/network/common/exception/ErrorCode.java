package com.uca.network.common.exception;

/**
 * @author admin
 */

public enum ErrorCode {
    FIELD_ERROR(1000),
    NAME_INVALID(1002, "输入参数不符合格式要求"),

    ERR_SUBNET_NOT_EXISTS(2001, "子网不存在"),

    ERR_NETWORK_NOT_EXISTS(3001, "网络不存在"),

    ERR_NETWORK_SUBNET_NOT_EXISTS(3002, "网络下没有子网"),

    ERR_NETWORK_USED(3003, "网络被使用"),

    ERR_ROUTER_NOT_EXISTS(4001, "路由器不存在"),

    IP_ALREADY_EXIST(10001, "ip:%s已经存在"),
    IP_POOL_IS_FULL(10002, "ip地址池已经耗尽"),
    MAC_ADDRESS_EXPLICIT(10003, "mac 地址冲突"),
    VXLAN_POOL_IS_FULL(10004, "vxlan域已经耗尽"),
    VLAN_POOL_IS_FULL(10005, "vlan在设备%s已经耗尽"),
    ;


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
