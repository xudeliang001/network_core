package com.uca.network.extension.mapper.subnet.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PublicSubnetDto {
    private String tenantId;
    private String name;
    private String id;
    private Date updatedAt;
    private String gatewayIp;
    private Date createdAt;
    private int ipVersion;
    private String cidr;
    private String networkId;
    private String standardAttrId;
    private String serviceType;
}
