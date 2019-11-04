package com.uca.network.core.subnet.service.impl;

import com.uca.network.common.config.ServerConfig;
import com.uca.network.common.exception.CommonException;
import com.uca.network.common.exception.ErrorCode;
import com.uca.network.common.exception.ErrorInfo;
import com.uca.network.common.utils.AddressUtils;
import com.uca.network.common.utils.DnsUtils;
import com.uca.network.core.mapper.attr.StandardattributesMapper;
import com.uca.network.core.mapper.attr.dto.StandardattributesDto;
import com.uca.network.core.mapper.dns.DnsNameServersMapper;
import com.uca.network.core.mapper.dns.dto.DnsNameServersDto;
import com.uca.network.core.mapper.ipallocationpools.IpallocationPoolsMapper;
import com.uca.network.core.mapper.ipallocationpools.dto.IpaAllocationpoolsDto;
import com.uca.network.core.mapper.subnet.SubnetMapper;
import com.uca.network.core.mapper.subnet.dto.SubnetDto;
import com.uca.network.core.network.service.impl.NetworkServiceImpl;
import com.uca.network.core.subnet.controller.model.SubnetReq;
import com.uca.network.core.subnet.service.SubnetService;
import com.uca.network.core.subnet.vo.SubnetVo;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "subnetServiceImpl")
public class SubnetServiceImpl implements SubnetService {

    private static final Logger logger = LoggerFactory.getLogger(SubnetServiceImpl.class);

    @Autowired
    private SubnetMapper subnetMapper;

    @Autowired
    private IpallocationPoolsMapper ipallocationPoolsMapper;

    @Autowired
    private StandardattributesMapper standardattributesMapper;

    @Autowired
    private DnsNameServersMapper dnsNameServersMapper;

    @Autowired
    private ServerConfig serverConfig;

    @Transactional
    @Override
    public SubnetVo createSubnet(SubnetReq subnetReq) {

        String subentId = UUID.randomUUID().toString().replace("-", "");
        Date createTime = new Date();
        Date updateTime = new Date();

        //计算ip pool
        String cidrBlock = subnetReq.getCidr();
        String ip = cidrBlock.substring(0, cidrBlock.indexOf("/"));
        String maskinfo = cidrBlock.substring(cidrBlock.indexOf("/") + 1, cidrBlock.length());
        String netMask = DnsUtils.getNetMask(maskinfo);
        String highAddress = DnsUtils.getHighAddr(ip, netMask);
        String lowAddress = DnsUtils.getLowAddr(ip, netMask);
        String finalhighAddr = AddressUtils.highAddressUtil(highAddress, serverConfig.getIpObligateHigh());
        String finallowAddr = AddressUtils.lowAddressUtil(lowAddress, serverConfig.getIpObligateLow());
        IpaAllocationpoolsDto ipaAllocationpoolsDto = new IpaAllocationpoolsDto();
        ipaAllocationpoolsDto.setId(UUID.randomUUID().toString());
        ipaAllocationpoolsDto.setFirstIp(finallowAddr);
        ipaAllocationpoolsDto.setLastIp(finalhighAddr);
        ipaAllocationpoolsDto.setSubnetId(subentId);
        ipallocationPoolsMapper.insertIpAllocationPools(ipaAllocationpoolsDto);


        StandardattributesDto standardattributesDto = new StandardattributesDto();
        standardattributesDto.setResourceType("subnets");
        standardattributesDto.setCreateTime(createTime);
        standardattributesDto.setUpdateTime(updateTime);
        standardattributesDto.setDescription(subnetReq.getDescription());
        String attrId = UUID.randomUUID().toString();
        standardattributesDto.setId(attrId);
        standardattributesMapper.insertStandardattributes(standardattributesDto);


        //插入subnet基础信息
        SubnetDto subnetDto = new SubnetDto();
        BeanUtils.copyProperties(subnetReq, subnetDto);
        subnetDto.setId(subentId);
        subnetDto.setGatewayIp(lowAddress);
        subnetDto.setCreateTime(createTime);
        subnetDto.setUpdateTime(updateTime);
        subnetDto.setStandardAttrId(attrId);
        subnetMapper.insertSubent(subnetDto);

        //插入dns信息
        String dnsStr = serverConfig.getDns();
        if (StringUtils.isNotEmpty(dnsStr)) {
            String[] dns = dnsStr.split(",");
            List<String> stringDns = Lists.newArrayList(dns);
            Collections.sort(stringDns);
            List<DnsNameServersDto> dnsNameServersDtos = new ArrayList<>();
            for (int i = 0; i < stringDns.size(); i++) {
                DnsNameServersDto dnsNameServersDto = new DnsNameServersDto();
                if (i == 0) {
                    dnsNameServersDto.setOrder(0);
                } else {
                    dnsNameServersDto.setOrder(1);
                }
                dnsNameServersDto.setAddress(stringDns.get(i));
                dnsNameServersDto.setSubnetId(subentId);
                dnsNameServersDto.setCreateTime(createTime);
                dnsNameServersDto.setUpdateTime(updateTime);
                dnsNameServersDtos.add(dnsNameServersDto);
            }
            dnsNameServersMapper.addDnsNameServersBatch(dnsNameServersDtos);
        }

        subnetReq.setGatewayIp(lowAddress);
        SubnetVo subnetVo = new SubnetVo();
        BeanUtils.copyProperties(subnetReq, subnetVo);
        return subnetVo;

    }

    @Transactional
    @Override
    public void deleteSubent(String tenantId, String subnetId, String networkId) {

        Map<String, Object> param = new HashMap<>();

        if (StringUtils.isEmpty(subnetId) && StringUtils.isEmpty(networkId)) {
            throw new RuntimeException();
        }

        if (StringUtils.isNotEmpty(subnetId)) {
            param.put("id", subnetId);
        }

        if (StringUtils.isNotEmpty(networkId)) {
            param.put("networkId", networkId);
        }

        List<SubnetDto> subnetDtos = subnetMapper.querySubnetsByParam(tenantId, networkId,
                null, null, subnetId);

        List<String> subnetIds = subnetDtos.stream().map(subnet -> subnet.getId()).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(subnetIds)) {

            Map<String, Object> paramPools = new HashMap<>();
            paramPools.put("subnetIds", subnetIds);
            ipallocationPoolsMapper.deleteIpAllocationPoolsByParam(paramPools);

            dnsNameServersMapper.deleteDnsNameServersBatch(subnetIds);

            List<String> attrIds = subnetDtos.stream().map(subnet -> subnet.getStandardAttrId()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(attrIds)) {
                standardattributesMapper.deleteStandardattributesBatch(attrIds);
            }
            subnetMapper.deleteSubnetByParam(param);

        } else {
            String msg = String.format("networkId : %s , subnetId : %s , tenantId : %s, subnet not exists", networkId, subnetId, tenantId);

            ErrorInfo errorInfo = new ErrorInfo(ErrorCode.ERR_SUBNET_NOT_EXISTS, "subnet", msg);

            throw new CommonException(errorInfo);
        }



    }

    @Override
    public List<SubnetVo> querySubnets(String tenantId) {
        return doQuerySubnets(tenantId, null, null);
    }

    @Override
    public List<SubnetVo> querySubnets(String tenantId, String networkId) {
        return doQuerySubnets(tenantId, networkId, null);
    }

    @Override
    public SubnetVo querySubnetDetail(String projectId, String subnetId) {
        List<SubnetVo> subnetVos = doQuerySubnets(projectId, null, subnetId);
        if (CollectionUtils.isEmpty(subnetVos)) {
            return new SubnetVo();
        } else {
            return subnetVos.get(0);
        }
    }

    public List<SubnetVo> querySubnetsAttr(String tenantId,String networkId,String subnetId ){
        List<SubnetDto> subnetDtos = subnetMapper.querySubnetsByParam(tenantId, networkId,
                null, null, subnetId);
        List<SubnetVo> subentVos = new ArrayList<>();
        subnetDtos.forEach(subnetDto -> {
            SubnetVo subnetVo = new SubnetVo();
            BeanUtils.copyProperties(subnetDto, subnetVo);
            subentVos.add(subnetVo);
        });
        return subentVos;
    }

    private List<SubnetVo> doQuerySubnets(String tenantId, String networkId, String subnetId) {

        List<SubnetDto> subnetDtos = subnetMapper.querySubnetsByParam(tenantId, networkId,
                null, null, subnetId);

        List<String> subnetIds = subnetDtos.stream().map(subnet -> subnet.getId()).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(subnetIds)) {

            List<IpaAllocationpoolsDto> ipaAllocationpoolsDtos = ipallocationPoolsMapper.
                    queryIpAllocationPoolsByParam(subnetIds);

            Map<String, List<IpaAllocationpoolsDto>> poolMap = ipaAllocationpoolsDtos.stream().collect(Collectors.groupingBy(IpaAllocationpoolsDto::getSubnetId));

            List<DnsNameServersDto> dnsLists = dnsNameServersMapper.queryDnsNameServersList(subnetIds);

            Map<String, List<DnsNameServersDto>> dnsMap = dnsLists.stream().
                    collect(Collectors.groupingBy(DnsNameServersDto::getSubnetId));

            List<String> attrIds = subnetDtos.stream().map(subnet -> subnet.getStandardAttrId()).collect(Collectors.toList());
            List<StandardattributesDto> standardattributesDtos = standardattributesMapper.queryAttrsByParam(attrIds);
            Map<String, List<StandardattributesDto>> attrMap = standardattributesDtos.stream().
                    collect(Collectors.groupingBy(StandardattributesDto::getId));

            List<SubnetVo> subentVos = new ArrayList<>();

            subnetDtos.forEach(subnetDto -> {
                SubnetVo subnetVo = new SubnetVo();
                BeanUtils.copyProperties(subnetDto, subnetVo);
                if (dnsMap.containsKey(subnetDto.getId())) {
                    List<DnsNameServersDto> dns = dnsMap.get(subnetDto.getId());
                    List<String> dnsStr = dns.stream().map(dnsDto -> dnsDto.getAddress()).collect(Collectors.toList());
                    subnetVo.setDns(dnsStr);

                }
                if (poolMap.containsKey(subnetDto.getId())) {
                    IpaAllocationpoolsDto allocationpoolsDto = poolMap.get(subnetDto.getId()).get(0);
                    Map<String, Object> ipPool = new HashMap<>();
                    ipPool.put("start", allocationpoolsDto.getFirstIp());
                    ipPool.put("end", allocationpoolsDto.getFirstIp());
                    subnetVo.setIpPools(ipPool);
                }
                if (attrMap.containsKey(subnetDto.getStandardAttrId())) {
                    subnetVo.setDescription(attrMap.get(subnetDto.getStandardAttrId()).get(0).getDescription());
                }
                subentVos.add(subnetVo);

            });

            return subentVos;

        } else {
            return Lists.newArrayList();
        }


    }



}
