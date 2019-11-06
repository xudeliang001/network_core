package com.uca.network.extension.allocations.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.uca.network.common.constant.CommonConstant;
import com.uca.network.common.exception.ErrorCode;
import com.uca.network.common.exception.AllocationsException;
import com.uca.network.common.utils.IpGeneratorUtils;
import com.uca.network.extension.allocations.service.IpAllocationsService;
import com.uca.network.extension.mapper.allocations.IpAllocationsMapper;
import com.uca.network.extension.mapper.allocations.model.IpAllocations;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/4 13:43
 */
public abstract class BaseIpAllocationsService implements IpAllocationsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IpAllocationsMapper ipAllocationsMapper;

    @Override
    public String allocateIp(String ipAddress, String subnetId, String firstIp, String lastIp) {
        if (StringUtils.isNotEmpty(ipAddress)) {
            return allocateFixedIp(ipAddress, subnetId, firstIp, lastIp);
        }
        List<IpAllocations> floatingIpList = ipAllocationsMapper.getMaxAllocatedIpFromSubnet(getTableName(), subnetId);
        if (CollectionUtils.isEmpty(floatingIpList)) {
            IpAllocations ipAllocations = IpAllocations.getFixedIp(firstIp, subnetId, IpGeneratorUtils.ipToLongValue(firstIp));
            try {
                ipAllocationsMapper.insert(getTableName(), ipAllocations);
                return firstIp;
            } catch (Exception e) {
                logger.error("first ip : {} had exist , do allocate ip", firstIp);
                return doAllocateIp(ipAllocations, subnetId, firstIp, lastIp);
            }
        }
        IpAllocations existIp = floatingIpList.get(0);
        return doAllocateIp(existIp, subnetId, firstIp, lastIp);
    }

    @Override
    public void releaseIp(String subnetId, String ipAddress) {
        List<IpAllocations> ipAllocationsList = ipAllocationsMapper.getAllocatedIpBySubnetAndIpAddress(getTableName(), subnetId, ipAddress);
        if (CollectionUtils.isEmpty(ipAllocationsList)) {
            return;
        }
        IpAllocations ipAllocations = ipAllocationsList.get(0);
        ipAllocationsMapper.releaseAllocatedIp(getTableName(), ipAllocations.getId());
    }

    /**
     * 申请当前最新的ip的下一个ip or复用释放的ip
     */
    private String doAllocateIp(IpAllocations existIp, String subnetId, String firstIp, String lastIp) {
        String ip = existIp.getIpAddress();
        while (true) {
            ip = IpGeneratorUtils.getNextIp(ip);
            long ipIndex = IpGeneratorUtils.ipToLongValue(ip);
            boolean isIpInRange = IpGeneratorUtils.isInRange(ip, firstIp, lastIp);
            if (!isIpInRange) {
                return reuseReleasedIp(subnetId);
            }
            IpAllocations ipAllocations = IpAllocations.getFixedIp(ip, subnetId, ipIndex);
            try {
                ipAllocationsMapper.insert(getTableName(), ipAllocations);
                return ip;
            } catch (Exception e) {
                logger.error("ip:{} had been used in subnet:{}", ip, subnetId);
            }
        }
    }

    /**
     * 复用已经释放的ip
     */
    private String reuseReleasedIp(String subnetId) {
        while (true) {
            List<IpAllocations> releasedIps = ipAllocationsMapper.getReleasedIpFromSubnet(getTableName(), subnetId);
            if (CollectionUtils.isEmpty(releasedIps)) {
                throw new AllocationsException(ErrorCode.IP_POOL_IS_FULL);
            }
            IpAllocations releasedIp = releasedIps.get(0);
            long ipIndex = IpGeneratorUtils.ipToLongValue(releasedIp.getIpAddress());
            int updateRows = ipAllocationsMapper.reuseReleasedIp(getTableName(), releasedIp.getId(), ipIndex);
            if (updateRows > 0) {
                return releasedIp.getIpAddress();
            }
        }
    }

    /**
     * 申请指定的ip地址
     */
    private String allocateFixedIp(String ipAddress, String subnetId, String firstIp, String lastIp) {
        Preconditions.checkArgument(IpGeneratorUtils.isInRange(ipAddress, firstIp, lastIp),
                String.format("IP %s is not in range : (%s , %s)", ipAddress, firstIp, lastIp));
        // 查询该ip是否是被释放的ip
        List<IpAllocations> existList = ipAllocationsMapper.getReleasedIpBySubnetAndIpAddress(getTableName(), subnetId, ipAddress);
        if (CollectionUtils.isNotEmpty(existList)) {
            IpAllocations existIp = existList.get(0);
            int updateRows = ipAllocationsMapper.reuseReleasedIp(getTableName(), existIp.getId(), CommonConstant.IP_FIXED_INDEX);
            if (updateRows > 0) {
                return ipAddress;
            }
            throw new AllocationsException(ErrorCode.IP_ALREADY_EXIST, ipAddress);
        }
        IpAllocations ipAllocations = IpAllocations.getFixedIp(ipAddress, subnetId, CommonConstant.IP_FIXED_INDEX);
        try {
            ipAllocationsMapper.insert(getTableName(), ipAllocations);
            return ipAddress;
        } catch (Exception e) {
            logger.error("allocate fixed ip : {} error", ipAddress, e);
        }
        throw new AllocationsException(ErrorCode.IP_ALREADY_EXIST, ipAddress);
    }

    public abstract String getTableName();
}
