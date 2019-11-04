package com.uca.network.core.subnet.service;

import com.uca.network.core.subnet.controller.model.SubnetReq;
import com.uca.network.core.subnet.vo.SubnetVo;

import java.util.List;

public interface SubnetService {

    public SubnetVo createSubnet(SubnetReq subnetReq);

    public void deleteSubent(String tenantId,String subnetId, String networkId);

    public List<SubnetVo> querySubnets(String tenantId);

    public List<SubnetVo> querySubnets(String tenantId, String networkId);

    public SubnetVo querySubnetDetail(String tenantId, String subnetId);

    public List<SubnetVo> querySubnetsAttr(String tenantId,String networkId,String subnetId );
}
