package com.uca.network.extension.plugin.floatingip.dto;

import lombok.Data;

import java.util.List;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/1
 */
@Data
public class FloatingIpToPluginDto {

    private String floatingIpAddress;
    private String txAverateLimit;
    private String rxAverateLimit;
    private String fixedIpAddress;
    private String floatingIpNum;
    private String fixedIpNum;
    private String protocal;
    private List<SubnetCidrDto> subnetCidrs;
    private String type;

}
