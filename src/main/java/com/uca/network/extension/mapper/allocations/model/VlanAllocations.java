package com.uca.network.extension.mapper.allocations.model;

import java.util.UUID;

import com.uca.network.common.constant.CommonConstant;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/5 16:33
 */
public class VlanAllocations {

    private String id;

    private String deviceId;

    private Long vlanId;

    private Integer allocated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getVlanId() {
        return vlanId;
    }

    public void setVlanId(Long vlanId) {
        this.vlanId = vlanId;
    }

    public Integer getAllocated() {
        return allocated;
    }

    public void setAllocated(Integer allocated) {
        this.allocated = allocated;
    }

    public static VlanAllocations getFixedVlan(String vxlanDomainId, long vxlanId) {
        VlanAllocations vlanAllocations = new VlanAllocations();
        vlanAllocations.setId(UUID.randomUUID().toString());
        vlanAllocations.setDeviceId(vxlanDomainId);
        vlanAllocations.setVlanId(vxlanId);
        vlanAllocations.setAllocated(CommonConstant.ALLOCATED_STATE);
        return vlanAllocations;
    }
}
