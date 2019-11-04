package com.uca.network.core.port.service.impl;

import com.alibaba.fastjson.JSON;
import com.uca.network.common.exception.CommonException;
import com.uca.network.common.exception.ErrorCode;
import com.uca.network.common.exception.ErrorInfo;
import com.uca.network.common.utils.ResourceStatus;
import com.uca.network.core.mapper.attr.StandardattributesMapper;
import com.uca.network.core.mapper.attr.dto.StandardattributesDto;
import com.uca.network.core.mapper.ipallocation.IpallocationsMapper;
import com.uca.network.core.mapper.ipallocation.dto.IpAllocationsDto;
import com.uca.network.core.mapper.network.NetworkMapper;
import com.uca.network.core.mapper.network.dto.NetworkDto;
import com.uca.network.core.mapper.ports.PortsMapper;
import com.uca.network.core.mapper.ports.dto.PortsDto;
import com.uca.network.core.mapper.subnet.SubnetMapper;
import com.uca.network.core.mapper.subnet.dto.SubnetDto;
import com.uca.network.core.port.controller.model.PortsReq;
import com.uca.network.core.port.service.PortsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PortsServiceImpl implements PortsService {

    private static final Logger logger = LoggerFactory.getLogger(PortsService.class);

    @Autowired
    private PortsMapper portsMapper;

    @Autowired
    private StandardattributesMapper standardattributesMapper;

    @Autowired
    private IpallocationsMapper ipallocationsMapper;

    @Autowired
    private NetworkMapper networkMapper;

    @Autowired
    private SubnetMapper subnetMapper;

    @Transactional
    @Override
    public void createPort(PortsReq portsReq) {

        logger.info("PortsServiceImpl createPort begin : {}", JSON.toJSONString(portsReq));

        String portId = UUID.randomUUID().toString().replace("-", "");
        String attrId = UUID.randomUUID().toString().replace("-", "");
        PortsDto portsDto = new PortsDto();
        BeanUtils.copyProperties(portsReq, portsDto);
        Date createTime = new Date();
        Date updateTime = new Date();
        portsDto.setId(portId);
        portsDto.setCreateTime(createTime);
        portsDto.setUpdateTime(updateTime);
        portsDto.setAdminStateUp(true);
        portsDto.setName(portsReq.getName());
        if(!StringUtils.isEmpty(portsReq.getDeviceId())&&!StringUtils.isEmpty(portsReq.getDeviceOwner())){
            portsDto.setStatus(ResourceStatus.ACTIVE.getVal());
        }else{
            portsDto.setStatus(ResourceStatus.DOWN.getVal());
        }

        portsDto.setStandardAttrId(attrId);
        portsDto.setTenantId(portsReq.getTenantId());
        portsDto.setDeviceId(portsReq.getDeviceId());
        portsDto.setDeviceOwner(portsReq.getDeviceOwner());
        portsDto.setNetworkId(portsReq.getNetworkId());
        // TODO: 2019/11/4 需要分配mac
        String macAddress = "1234";
        portsDto.setMacAddress(macAddress);
        portsDto.setStandardAttrId(attrId);
        portsMapper.insertPort(portsDto);

        StandardattributesDto standardattributesDto = new StandardattributesDto();
        standardattributesDto.setResourceType("ports");
        standardattributesDto.setCreateTime(createTime);
        standardattributesDto.setUpdateTime(updateTime);
        standardattributesDto.setDescription("");
        standardattributesDto.setId(attrId);
        standardattributesMapper.insertStandardattributes(standardattributesDto);

        // TODO: 2019/11/4 需要分配ip

        List<NetworkDto> networks = networkMapper.queryNetworksByParam(portsReq.getTenantId(),
                portsReq.getNetworkId(), null);

        if (CollectionUtils.isEmpty(networks)) {

            String msg = String.format("networkId : %s , tenantId : %s", portsReq.getNetworkId(), portsReq.getTenantId());

            ErrorInfo errorInfo = new ErrorInfo(ErrorCode.ERR_NETWORK_NOT_EXISTS, "ports", msg);

            throw new CommonException(errorInfo);
        }
        List<SubnetDto> subnetDtos = null;
        if (!StringUtils.isEmpty(portsReq.getSubnetId())) {
            subnetDtos = subnetMapper.querySubnetsByParam(portsReq.getTenantId(), portsReq.getNetworkId(),
                    null, null, portsReq.getSubnetId());
        } else {
            subnetDtos = subnetMapper.querySubnetsByParam(portsReq.getTenantId(), portsReq.getNetworkId(),
                    null, null, null);
        }
        if (CollectionUtils.isEmpty(subnetDtos)) {

            String msg = String.format("networkId : %s , tenantId : %s", portsReq.getNetworkId(), portsReq.getTenantId());

            ErrorInfo errorInfo = new ErrorInfo(ErrorCode.ERR_NETWORK_SUBNET_NOT_EXISTS, "ports", msg);

            throw new CommonException(errorInfo);
        }
        SubnetDto subnetDto = subnetDtos.get(0);
        String ipAddress = "";
        IpAllocationsDto ipAllocationsDto = new IpAllocationsDto();
        ipAllocationsDto.setPortId(portId);
        ipAllocationsDto.setNetworkId(portsReq.getNetworkId());
        ipAllocationsDto.setSubnetId(subnetDto.getId());
        ipAllocationsDto.setIpAddress(ipAddress);
        ipAllocationsDto.setCreateTime(createTime);
        ipAllocationsDto.setUpdateTime(updateTime);
        ipallocationsMapper.insertIpAllocations(ipAllocationsDto);

    }

    @Transactional
    @Override
    public void deletePort(String tenantId, String portId) {
        portsMapper.deletPortById(tenantId,portId);
        ipallocationsMapper.deleteIpAllocationsByPortId(portId);

    }

    @Override
    public List<PortsDto> queryPortsAttrByNetworkId(String tenantId, String networkId) {
        return portsMapper.queryPortsByParam(null, null,
                tenantId, networkId, null, null, null, null);
    }
}
