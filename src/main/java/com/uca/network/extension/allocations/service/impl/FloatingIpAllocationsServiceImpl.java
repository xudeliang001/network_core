package com.uca.network.extension.allocations.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/5 10:44
 */
@Lazy
@Service("floatingIpAllocationsService")
public class FloatingIpAllocationsServiceImpl extends BaseIpAllocationsService {

    @Override
    public String getTableName() {
        return "tbl_floating_ip_allocations";
    }
}
