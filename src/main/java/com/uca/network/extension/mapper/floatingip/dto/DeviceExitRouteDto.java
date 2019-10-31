package com.uca.network.extension.mapper.floatingip.dto;

import lombok.Data;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
@Data
public class DeviceExitRouteDto {
    private String id;
    private String deviceIp;
    private String portNum;
    private String userName;
    private String userPassword;
}
