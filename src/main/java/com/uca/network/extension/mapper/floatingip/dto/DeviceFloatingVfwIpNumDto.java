package com.uca.network.extension.mapper.floatingip.dto;

import lombok.Data;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/5
 */
@Data
public class DeviceFloatingVfwIpNumDto {
    private String id;
    private String floatingIpAddress;
    private String fixedIpAddress;
    private String floatingIpNum;
    private String fixedIpNum;
    private String protocal;
}
