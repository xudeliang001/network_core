package com.uca.network.core.network.service.impl;

import com.alibaba.fastjson.JSON;
import com.uca.network.common.exception.CommonException;
import com.uca.network.common.exception.ErrorCode;
import com.uca.network.common.exception.ErrorInfo;
import com.uca.network.common.utils.ResourceStatus;
import com.uca.network.core.mapper.attr.StandardattributesMapper;
import com.uca.network.core.mapper.attr.dto.StandardattributesDto;
import com.uca.network.core.mapper.network.NetworkMapper;
import com.uca.network.core.mapper.network.dto.NetworkDto;
import com.uca.network.core.mapper.ports.PortsMapper;
import com.uca.network.core.mapper.ports.dto.PortsDto;
import com.uca.network.core.network.controller.model.NetworkReq;
import com.uca.network.core.network.service.NetworkService;
import com.uca.network.core.network.vo.NetworkVo;
import com.uca.network.core.port.service.PortsService;
import com.uca.network.core.subnet.service.SubnetService;
import com.uca.network.core.subnet.vo.SubnetVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NetworkServiceImpl implements NetworkService {

    private static final Logger logger = LoggerFactory.getLogger(NetworkServiceImpl.class);

    @Autowired
    private NetworkMapper networkMapper;

    @Autowired
    private StandardattributesMapper standardattributesMapper;

    @Autowired
    private SubnetService subnetService;

    @Autowired
    private PortsService portsService;

    @Transactional
    @Override
    public NetworkVo createNetwork(NetworkReq networkReq) {
        logger.info("NetworkServiceImpl createNetwork begin : {} ", JSON.toJSONString(networkReq));

        String networkId = UUID.randomUUID().toString().replace("-", "");
        Date createTime = new Date();
        Date updateTime = new Date();
        StandardattributesDto standardattributesDto = new StandardattributesDto();
        standardattributesDto.setResourceType("networks");
        standardattributesDto.setCreateTime(createTime);
        standardattributesDto.setUpdateTime(updateTime);
        standardattributesDto.setDescription(networkReq.getDescription());
        String attrId = UUID.randomUUID().toString().replace("-", "");
        standardattributesDto.setId(attrId);
        standardattributesMapper.insertStandardattributes(standardattributesDto);

        NetworkDto networkDto = new NetworkDto();
        BeanUtils.copyProperties(networkReq, networkDto);
        networkDto.setId(networkId);
        networkDto.setStandardAttrId(attrId);
        networkDto.setStatus(ResourceStatus.ACTIVE.getVal());
        networkDto.setUpdateTime(updateTime);
        networkDto.setCreateTime(createTime);
        networkDto.setStandardAttrId(attrId);
        networkDto.setAdminStateUp(true);
        networkDto.setMtu(1500);

        networkMapper.insertNetwork(networkDto);

        NetworkVo networkVo = new NetworkVo();
        BeanUtils.copyProperties(networkDto, networkVo);
        return networkVo;
    }

    @Override
    public List<NetworkVo> queryNetworks(String tenantId) {
        return doQueryNetworks(tenantId, null);
    }

    @Override
    public NetworkVo queryNetworkDetail(String tenantId, String networkId) {
        List<NetworkVo> networkVos = doQueryNetworks(tenantId, networkId);
        if (CollectionUtils.isEmpty(networkVos)) {
            return new NetworkVo();
        } else {
            return networkVos.get(0);
        }
    }

    @Transactional
    @Override
    public void deleteNetwork(String tenantId, String networkId) {
        logger.info("NetworkServiceImpl deleteNetwork begin : {},{}", tenantId, networkId);
        List<NetworkDto> networkDtos = networkMapper.queryNetworksByParam(tenantId, networkId, null);
        if (CollectionUtils.isEmpty(networkDtos)) {
            String msg = String.format("networkId : %s , tenantId : %s, network not exists", networkId, tenantId);

            ErrorInfo errorInfo = new ErrorInfo(ErrorCode.ERR_NETWORK_NOT_EXISTS, "network", msg);
            throw new CommonException(errorInfo);
        }

        //网络被使用不允许删除
        NetworkDto networkDto = networkDtos.get(0);

        //查询虚拟网卡信息
        List<PortsDto> portsDtos = portsService.queryPortsAttrByNetworkId(tenantId, networkId);
        int portCount = 0;
        PortsDto portsDto = null;
        for (PortsDto port : portsDtos) {
            if (!"network:router_interface".
                    equals(port.getDeviceOwner())) {
                portCount++;
            } else {
                portsDto = port;
            }
        }

        if (portCount > 0) {
            String msg = String.format("networkId : %s , tenantId : %s", networkId, tenantId);

            ErrorInfo errorInfo = new ErrorInfo(ErrorCode.ERR_NETWORK_USED, "network", msg);
            throw new CommonException(errorInfo);
        }

        //删除虚拟网卡(绑定路由器的)
        if (portsDto != null) {
            portsService.deletePort(tenantId,portsDto.getId());
        }

        //删除网络
        networkMapper.deleteNetworkById(networkId);
        //删除属性
        standardattributesMapper.deleteStandardattributesById(networkDto.getStandardAttrId());

        //删除子网
        List<SubnetVo> checkList = subnetService.querySubnetsAttr(tenantId, networkId, null);
        if (!CollectionUtils.isEmpty(checkList)) {
            subnetService.deleteSubent(tenantId, null, networkId);
        }

    }

    private List<NetworkVo> doQueryNetworks(String tenantId, String networkId) {
        List<NetworkDto> networkDtos = networkMapper.queryNetworksByParam(tenantId, null, null);
        List<NetworkVo> networkVoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(networkDtos)) {
            List<String> attrIds = networkDtos.stream().map(network -> network.getStandardAttrId()).collect(Collectors.toList());
            List<StandardattributesDto> standardattributesDtos = standardattributesMapper.queryAttrsByParam(attrIds);
            Map<String, List<StandardattributesDto>> attrMap = standardattributesDtos.stream().
                    collect(Collectors.groupingBy(StandardattributesDto::getId));
            networkDtos.forEach(networkDto -> {
                NetworkVo networkVo = new NetworkVo();
                BeanUtils.copyProperties(networkDto, networkVo);
                if (attrMap.containsKey(networkDto.getStandardAttrId())) {
                    networkVo.setDescription(attrMap.get(networkDto.getStandardAttrId()).get(0).getDescription());
                }
                networkVoList.add(networkVo);
            });

        }
        return networkVoList;
    }
}
