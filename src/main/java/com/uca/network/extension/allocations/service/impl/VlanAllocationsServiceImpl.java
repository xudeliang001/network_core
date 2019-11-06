package com.uca.network.extension.allocations.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.uca.network.common.exception.AllocationsException;
import com.uca.network.common.exception.ErrorCode;
import com.uca.network.extension.allocations.service.VlanAllocationsService;
import com.uca.network.extension.mapper.allocations.VlanAllocationsMapper;
import com.uca.network.extension.mapper.allocations.model.VlanAllocations;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/5 16:42
 */
@Lazy
@Service
public class VlanAllocationsServiceImpl implements VlanAllocationsService {

    private Logger logger = LoggerFactory.getLogger(VlanAllocationsServiceImpl.class);

    @Autowired
    private VlanAllocationsMapper vlanAllocationsMapper;

    @Override
    public long allocateVlanId(String deviceId, long firstVlanId, long lastVlanId) {
        List<VlanAllocations> vlanList = vlanAllocationsMapper.getMaxAllocatedVlanFromDomain(deviceId);
        if (CollectionUtils.isEmpty(vlanList)) {
            VlanAllocations vlanAllocations = VlanAllocations.getFixedVlan(deviceId, firstVlanId);
            try {
                vlanAllocationsMapper.insert(vlanAllocations);
                return firstVlanId;
            } catch (Exception e) {
                logger.error("first vlan id : {} had exist , do allocate vlan id", firstVlanId);
                return doAllocateVlanId(vlanAllocations, deviceId, firstVlanId, lastVlanId);
            }
        }
        VlanAllocations existVlan = vlanList.get(0);
        return doAllocateVlanId(existVlan, deviceId, firstVlanId, lastVlanId);
    }

    @Override
    public void releaseVlanId(String deviceId, long vlanId) {
        List<VlanAllocations> existList = vlanAllocationsMapper.getAllocatedVlanByDomainAndVlanId(deviceId, vlanId);
        if (CollectionUtils.isNotEmpty(existList)) {
            vlanAllocationsMapper.releaseAllocatedVlan(existList.get(0).getId());
        }
    }

    /**
     * 申请当前最新的ip的下一个ip or复用释放的ip
     */
    private long doAllocateVlanId(VlanAllocations existVlan, String deviceId, long firstVlanId, long lastVlanId) {
        long vlanId = existVlan.getVlanId();
        while (true) {
            vlanId++;
            if (!isInRange(vlanId, firstVlanId, lastVlanId)) {
                return reuseReleasedVlanId(deviceId);
            }
            VlanAllocations vlanAllocations = VlanAllocations.getFixedVlan(deviceId, vlanId);
            try {
                vlanAllocationsMapper.insert(vlanAllocations);
                return vlanId;
            } catch (Exception e) {
                logger.error("vlan id:{} had been used in device:{}", vlanId, deviceId);
            }
        }
    }

    /**
     * 复用已经释放的vxlan
     */
    private long reuseReleasedVlanId(String deviceId) {
        while (true) {
            List<VlanAllocations> releasedVlans = vlanAllocationsMapper.getReleasedVlanFromDomain(deviceId);
            if (CollectionUtils.isEmpty(releasedVlans)) {
                throw new AllocationsException(ErrorCode.VLAN_POOL_IS_FULL, deviceId);
            }
            VlanAllocations releasedVlan = releasedVlans.get(0);
            int updateRows = vlanAllocationsMapper.reuseReleasedVlan(releasedVlan.getId());
            if (updateRows > 0) {
                return releasedVlan.getVlanId();
            }
        }
    }

    private boolean isInRange(long vlanId, long firstVlanId, long lastVlanId) {
        return vlanId >= firstVlanId && vlanId <= lastVlanId;
    }
}
