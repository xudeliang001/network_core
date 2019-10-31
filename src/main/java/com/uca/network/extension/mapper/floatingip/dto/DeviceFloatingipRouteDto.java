package com.uca.network.extension.mapper.floatingip.dto;

import lombok.Data;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
@Data
public class DeviceFloatingipRouteDto {
    private String id;
    private String floatingIpAddress;
    private String txAverateLimit;
    private String rxAverateLimit;
    private String deviceIp;
}
