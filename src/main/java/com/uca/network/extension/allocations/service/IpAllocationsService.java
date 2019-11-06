package com.uca.network.extension.allocations.service;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/4 13:32
 */
public interface IpAllocationsService {

    /**
     * 在指定子网中申请ip
     *
     * @param ipAddress 用户指定的ip地址
     * @param subnetId  申请的子网id，唯一标志
     * @param firstIp subnet对应的地址池起始位置(include)
     * @param lastIp subnet对应的地址池终止位置(include)
     */
    String allocateIp(String ipAddress, String subnetId, String firstIp, String lastIp);

    /**
     * 释放指定的ip地址
     */
    void releaseIp(String subnetId, String ipAddress);

}
