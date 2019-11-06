package com.uca.network.extension.mapper.allocations;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uca.network.extension.mapper.allocations.model.VxlanAllocations;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/4 11:33
 */
public interface VxlanAllocationsMapper {

    /**
     * 写入数据库
     */
    void insert(VxlanAllocations vxlanAllocations);

    /**
     * 查询指定域最新的已被分配的最大vxlan id
     */
    List<VxlanAllocations> getMaxAllocatedVxlanFromDomain(String vxlanDomainId);

    /**
     * 根据vxlanDomainId与vxlanId查询已经申请的记录
     */
    List<VxlanAllocations> getAllocatedVxlanByDomainAndVxlanId(@Param("vxlanDomainId") String vxlanDomainId, @Param("vxlanId") Long vxlanId);

    /**
     * 查询指定domain中被释放的记录
     */
    List<VxlanAllocations> getReleasedVxlanFromDomain(@Param("vxlanDomainId") String vxlanDomainId);

    /**
     * 复用已释放的vxlan
     */
    int reuseReleasedVxlan(@Param("id") String id);

    /**
     * 释放已经使用的vxlan
     */
    int releaseAllocatedVxlan(@Param("id") String id);
}
