package com.uca.network.extension.mapper.reservedip.dto;

import lombok.Data;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
@Data
public class ReservedIpDto {

    private String ipAddress;
    private String subnetId;
    private String networkId;
    private int allocated;
    private String reservedTenantId;
    private String reservedDescription;
}
