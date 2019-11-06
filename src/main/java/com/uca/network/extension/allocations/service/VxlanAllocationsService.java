package com.uca.network.extension.allocations.service;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/4 13:32
 */
public interface VxlanAllocationsService {

    /**
     * 在指定子网中申请ip
     *
     * @param vxlanDomainId  申请的vxlan域id，唯一标志
     * @param firstVxlanId xlan域对应的地址池起始位置(include)
     * @param lastVxlanId xlan域对应的地址池终止位置(include)
     */
    long allocateVxlanId(String vxlanDomainId, long firstVxlanId, long lastVxlanId);

    /**
     * 释放指定的vxlan
     */
    void releaseVxlanId(String vxlanDomainId, long vxlanId);

}
