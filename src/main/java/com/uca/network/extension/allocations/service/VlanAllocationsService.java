package com.uca.network.extension.allocations.service;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/4 13:32
 */
public interface VlanAllocationsService {

    /**
     * 在指定子网中申请ip
     *
     * @param deviceId  申请的vlan域id，唯一标志
     * @param firstVlanId vlan域对应的地址池起始位置(include)
     * @param lastVlanId vlan域对应的地址池终止位置(include)
     */
    long allocateVlanId(String deviceId, long firstVlanId, long lastVlanId);

    /**
     * 释放指定的vlan
     */
    void releaseVlanId(String vlanDomainId, long vlanId);

}
