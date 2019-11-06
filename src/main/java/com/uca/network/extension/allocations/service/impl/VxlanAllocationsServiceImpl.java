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
import com.uca.network.extension.allocations.service.VxlanAllocationsService;
import com.uca.network.extension.mapper.allocations.VxlanAllocationsMapper;
import com.uca.network.extension.mapper.allocations.model.VxlanAllocations;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/5 16:42
 */
@Lazy
@Service
public class VxlanAllocationsServiceImpl implements VxlanAllocationsService {

    private Logger logger = LoggerFactory.getLogger(VxlanAllocationsServiceImpl.class);

    @Autowired
    private VxlanAllocationsMapper vxlanAllocationsMapper;

    @Override
    public long allocateVxlanId(String vxlanDomainId, long firstVxlanId, long lastVxlanId) {
        List<VxlanAllocations> vxlanList = vxlanAllocationsMapper.getMaxAllocatedVxlanFromDomain(vxlanDomainId);
        if (CollectionUtils.isEmpty(vxlanList)) {
            VxlanAllocations vxlanAllocations = VxlanAllocations.getFixedVxlan(vxlanDomainId, firstVxlanId);
            try {
                vxlanAllocationsMapper.insert(vxlanAllocations);
                return firstVxlanId;
            } catch (Exception e) {
                logger.error("first vxlan id : {} had exist , do allocate vxlan id", firstVxlanId);
                return doAllocateVxlanId(vxlanAllocations, vxlanDomainId, firstVxlanId, lastVxlanId);
            }
        }
        VxlanAllocations existVxlan = vxlanList.get(0);
        return doAllocateVxlanId(existVxlan, vxlanDomainId, firstVxlanId, lastVxlanId);
    }

    @Override
    public void releaseVxlanId(String vxlanDomainId, long vxlanId) {
        List<VxlanAllocations> existList = vxlanAllocationsMapper.getAllocatedVxlanByDomainAndVxlanId(vxlanDomainId, vxlanId);
        if (CollectionUtils.isNotEmpty(existList)) {
            vxlanAllocationsMapper.releaseAllocatedVxlan(existList.get(0).getId());
        }
    }

    /**
     * 申请当前最新的ip的下一个ip or复用释放的ip
     */
    private long doAllocateVxlanId(VxlanAllocations existVxlan, String vxlanDomainId, long firstVxlanId, long lastVxlanId) {
        long vxlanId = existVxlan.getVxlanId();
        while (true) {
            vxlanId++;
            if (!isInRange(vxlanId, firstVxlanId, lastVxlanId)) {
                return reuseReleasedVxlanId(vxlanDomainId);
            }
            VxlanAllocations vxlanAllocations = VxlanAllocations.getFixedVxlan(vxlanDomainId, vxlanId);
            try {
                vxlanAllocationsMapper.insert(vxlanAllocations);
                return vxlanId;
            } catch (Exception e) {
                logger.error("vxlan id:{} had been used in domain:{}", vxlanId, vxlanDomainId);
            }
        }
    }

    /**
     * 复用已经释放的vxlan
     */
    private long reuseReleasedVxlanId(String vxlanDomainId) {
        while (true) {
            List<VxlanAllocations> releasedVxlans = vxlanAllocationsMapper.getReleasedVxlanFromDomain(vxlanDomainId);
            if (CollectionUtils.isEmpty(releasedVxlans)) {
                throw new AllocationsException(ErrorCode.VXLAN_POOL_IS_FULL);
            }
            VxlanAllocations releasedVxlan = releasedVxlans.get(0);
            int updateRows = vxlanAllocationsMapper.reuseReleasedVxlan(releasedVxlan.getId());
            if (updateRows > 0) {
                return releasedVxlan.getVxlanId();
            }
        }
    }

    private boolean isInRange(long vxlanId, long firstVxlanId, long lastVxlanId) {
        return vxlanId >= firstVxlanId && vxlanId <= lastVxlanId;
    }
}
