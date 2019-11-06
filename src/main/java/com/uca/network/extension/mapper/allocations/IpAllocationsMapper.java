package com.uca.network.extension.mapper.allocations;

import com.uca.network.extension.mapper.allocations.model.IpAllocations;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/4 11:33
 */
public interface IpAllocationsMapper {

    /**
     * 写入数据库
     */
    void insert(@Param("tableName") String tableName, @Param("ipAllocations") IpAllocations floatingIpAllocations);

    /**
     * 查询指定子网最新的已被分配的ip地址
     */
    List<IpAllocations> getMaxAllocatedIpFromSubnet(@Param("tableName") String tableName, @Param("subnetId") String subnetId);

    /**
     * 根据subnetId与ip地址查询被释放的记录
     */
    List<IpAllocations> getReleasedIpBySubnetAndIpAddress(@Param("tableName") String tableName, @Param("subnetId") String subnetId,
                                                          @Param("ipAddress") String ipAddress);

    /**
     * 根据subnetId与ip地址查询已经申请的记录
     */
    List<IpAllocations> getAllocatedIpBySubnetAndIpAddress(@Param("tableName") String tableName, @Param("subnetId") String subnetId,
                                                          @Param("ipAddress") String ipAddress);

    /**
     * 查询指定subnet中被释放的记录
     */
    List<IpAllocations> getReleasedIpFromSubnet(@Param("tableName") String tableName, @Param("subnetId") String subnetId);

    /**
     * 复用已释放的ip,并重置索引值
     */
    int reuseReleasedIp(@Param("tableName") String tableName, @Param("id") String id, @Param("ipIndex") long ipIndex);

    /**
     * 释放已经使用的ip
     */
    int releaseAllocatedIp(@Param("tableName") String tableName, @Param("id") String id);
}
