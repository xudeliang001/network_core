package com.uca.network.extension.mapper.floatingip.dto;

import lombok.Data;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
@Data
public class FloatingVfwDto {
    private String id;
    private String floatingIpAddress;
    private String txAverateLimit;
    private String rxAverateLimit;
    private String fixedIpAddress;
    private String floatingIpNum;
    private String fixedIpNum;
    private String protocal;
    private String subnetCidr;
    private String addressGroupId;
    private String addressGroupName;
    private String vpnInstanceId;
    private String aclName;
    private String type;
}
