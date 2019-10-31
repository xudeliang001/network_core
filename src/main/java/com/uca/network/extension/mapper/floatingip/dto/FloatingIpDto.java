package com.uca.network.extension.mapper.floatingip.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FloatingIpDto {
    private String tenantId;
    private String id;
    private String name;
    private String floatingIpAddress;
    private String floatingNetworkId;
    private String fixedPortId;
    private String fixedIpAddress;
    private String routeId;
    private String lastKnownRouteId;
    private String status;
    private String standardAttrId;
    private String txAverateLimit;
    private String rxAverateLimit;
    private String lineType;
    private String extra;
    private Date createdAt;
    private Date updatedAt;
}
