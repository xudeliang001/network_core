package com.uca.network.extension.mapper.allocations.model;

import com.uca.network.common.constant.CommonConstant;

import java.util.UUID;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/5 16:33
 */
public class VxlanAllocations {

    private String id;

    private String vxlanDomainId;

    private Long vxlanId;

    private Integer allocated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVxlanDomainId() {
        return vxlanDomainId;
    }

    public void setVxlanDomainId(String vxlanDomainId) {
        this.vxlanDomainId = vxlanDomainId;
    }

    public Long getVxlanId() {
        return vxlanId;
    }

    public void setVxlanId(Long vxlanId) {
        this.vxlanId = vxlanId;
    }

    public Integer getAllocated() {
        return allocated;
    }

    public void setAllocated(Integer allocated) {
        this.allocated = allocated;
    }

    public static VxlanAllocations getFixedVxlan(String vxlanDomainId, long vxlanId) {
        VxlanAllocations vxlanAllocations = new VxlanAllocations();
        vxlanAllocations.setId(UUID.randomUUID().toString());
        vxlanAllocations.setVxlanDomainId(vxlanDomainId);
        vxlanAllocations.setVxlanId(vxlanId);
        vxlanAllocations.setAllocated(CommonConstant.ALLOCATED_STATE);
        return vxlanAllocations;
    }
}
