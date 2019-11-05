package com.uca.network.extension.mapper.floatingip.dto;

import lombok.Data;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/5
 */
@Data
public class DeviceFloatingVfwSubnetDto {
    private String id;
    private String floatingIpAddress;
    private String srcSubnetCidr;
    private String dstSubnetCidr;
}
