package com.uca.network.extension.plugin.floatingip.dto;

import lombok.Data;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/5
 */
@Data
public class SubnetCidrDto {
    private String srcSubnetCidr;
    private String dstSubnetCidr;
}
