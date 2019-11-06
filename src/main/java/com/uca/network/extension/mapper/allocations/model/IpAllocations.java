package com.uca.network.extension.mapper.allocations.model;

import java.util.UUID;

import static com.uca.network.common.constant.CommonConstant.*;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/4 11:22
 */
public class IpAllocations {

    private String id;

    private String subnetId;

    private String ipAddress;

    private Integer allocated;

    private Long ipIndex;

    private String reservedTenantId;

    private String reservedDescription;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getAllocated() {
        return allocated;
    }

    public void setAllocated(Integer allocated) {
        this.allocated = allocated;
    }

    public Long getIpIndex() {
        return ipIndex;
    }

    public void setIpIndex(Long ipIndex) {
        this.ipIndex = ipIndex;
    }

    public String getReservedTenantId() {
        return reservedTenantId;
    }

    public void setReservedTenantId(String reservedTenantId) {
        this.reservedTenantId = reservedTenantId;
    }

    public String getReservedDescription() {
        return reservedDescription;
    }

    public void setReservedDescription(String reservedDescription) {
        this.reservedDescription = reservedDescription;
    }

    public static IpAllocations getFixedIp(String ipAddress, String subnet, long ipIndex) {
        IpAllocations ipAllocations = new IpAllocations();
        ipAllocations.setId(UUID.randomUUID().toString());
        ipAllocations.setIpAddress(ipAddress);
        ipAllocations.setSubnetId(subnet);
        ipAllocations.setIpIndex(ipIndex);
        ipAllocations.setAllocated(ALLOCATED_STATE);
        return ipAllocations;
    }
}
