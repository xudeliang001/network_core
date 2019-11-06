package com.uca.network.extension.mapper.allocations;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uca.network.extension.mapper.allocations.model.VlanAllocations;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/4 11:33
 */
public interface VlanAllocationsMapper {

    /**
     * 写入数据库
     */
    void insert(VlanAllocations vlanAllocations);

    /**
     * 查询指定域最新的已被分配的最大vlan id
     */
    List<VlanAllocations> getMaxAllocatedVlanFromDomain(String deviceId);

    /**
     * 根据与vlanId查询已经申请的记录
     */
    List<VlanAllocations> getAllocatedVlanByDomainAndVlanId(@Param("deviceId") String deviceId, @Param("vlanId") Long vlanId);

    /**
     * 查询指定设备中被释放的记录
     */
    List<VlanAllocations> getReleasedVlanFromDomain(@Param("deviceId") String deviceId);

    /**
     * 复用已释放的vlan
     */
    int reuseReleasedVlan(@Param("id") String id);

    /**
     * 释放已经使用的vlan
     */
    int releaseAllocatedVlan(@Param("id") String id);
}
