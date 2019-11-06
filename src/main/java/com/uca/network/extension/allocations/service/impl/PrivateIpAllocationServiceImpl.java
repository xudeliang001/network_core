package com.uca.network.extension.allocations.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/5 13:31
 */
@Lazy
@Service("privateIpAllocationService")
@Primary
public class PrivateIpAllocationServiceImpl extends BaseIpAllocationsService {

    @Override
    public String getTableName() {
        return "tbl_private_ip_allocations";
    }
}
