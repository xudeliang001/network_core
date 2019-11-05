package com.uca.network.extension.plugin.floatingip.service;

import com.alibaba.fastjson.JSON;
import com.uca.network.extension.mapper.floatingip.DeviceExitRouteMapper;
import com.uca.network.extension.mapper.floatingip.DeviceFloatingIpVfwMapper;
import com.uca.network.extension.mapper.floatingip.DeviceFloatingipRouteMapper;
import com.uca.network.extension.mapper.floatingip.dto.*;
import com.uca.network.extension.plugin.floatingip.agent.RouteService;
import com.uca.network.extension.plugin.floatingip.agent.VfwService;
import com.uca.network.extension.plugin.floatingip.agent.dto.EipRateLimitDto;
import com.uca.network.extension.plugin.floatingip.dto.FloatingIpToPluginDto;
import com.uca.network.extension.plugin.floatingip.dto.SubnetCidrDto;
import com.uca.network.extension.plugin.floatingip.dto.VfwInfoDto;
import com.uca.network.extension.plugin.floatingip.util.FloatingIpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
@Service
public class FloatingipPluginService {
    private static final Logger logger = LoggerFactory.getLogger(FloatingipPluginService.class);

    @Autowired
    private DeviceFloatingIpVfwMapper deviceFloatingIpVfwMapper;
    @Autowired
    private DeviceFloatingipRouteMapper deviceFloatingipRouteMapper;
    @Autowired
    private DeviceExitRouteMapper deviceExitRouteMapper;
    @Autowired
    private FloatingIpUtil floatingIpUtil;
    @Autowired
    private VfwService vfwService;
    @Autowired
    private RouteService routeService;

    /**
     * @param floatingIpToPluginDto
     * @return
     */
    @Transactional
    public Boolean bindFloatingIpToAgent(FloatingIpToPluginDto floatingIpToPluginDto){
        logger.info("FloatingipPluginService-bindFloatingIpToAgent start-floatingIpToPluginDto:{}", JSON.toJSONString(floatingIpToPluginDto));
        //floatingIpToPluginDto
        if (floatingIpToPluginDto == null){
            logger.error("floatingIpToPluginDto is null");
            return false;
        }
        //校验不同类型 dnat、dsnat、snat 传入的参数
        switch (floatingIpToPluginDto.getType()) {
            case "dnat":
                if (floatingIpToPluginDto.getFixedIpAddress() == null || floatingIpToPluginDto.getFloatingIpAddress() == null){
                    logger.error("dsnat:param is invalid!");
                    return false;
                }
                //组装存库数据和下发agent vfw数据
                createDnatToVfw(floatingIpToPluginDto);
                break;
            case "dsnat":
                boolean result = floatingIpToPluginDto.getFixedIpAddress() == null || floatingIpToPluginDto.getFloatingIpAddress() == null ||
                        floatingIpToPluginDto.getProtocal() == null || (floatingIpToPluginDto.getFixedIpNum() == null && floatingIpToPluginDto.getFloatingIpNum() != null) ||
                        (floatingIpToPluginDto.getFixedIpNum() != null && floatingIpToPluginDto.getFloatingIpNum() == null);
                if (result){
                    logger.error("dnat:param is invalid!");
                    return false;
                }
                //组装存库数据和下发agent数据
                createDsnatToVfw(floatingIpToPluginDto);
                break;
            case "snat":
                if (CollectionUtils.isEmpty(floatingIpToPluginDto.getSubnetCidrs()) || floatingIpToPluginDto.getFloatingIpAddress() == null){
                    logger.error("snat:param is invalid!");
                    return false;
                }
                //组装存库数据和下发agent数据
                createSnatToVfw(floatingIpToPluginDto);
                break;
            default:
                break;
        }
        //下发带宽
        createBandwidthToRoute(floatingIpToPluginDto);
        return true;
    }

    private void createBandwidthToRoute(FloatingIpToPluginDto floatingIpToPluginDto) {
        //封装带宽数据
        List<DeviceFloatingipRouteDto> deviceFloatingipRouteDtos = new ArrayList<>();
        List<DeviceExitRouteDto> deviceExitRouteDtos = deviceExitRouteMapper.queryExitRoute();
        List<EipRateLimitDto> eipRateLimitDtos = new ArrayList<>();

        deviceExitRouteDtos.forEach(deviceExitRouteDto1 -> {
            DeviceFloatingipRouteDto deviceFloatingipRouteDto = new DeviceFloatingipRouteDto();
            deviceFloatingipRouteDto.setDeviceIp(deviceExitRouteDto1.getDeviceIp());
            deviceFloatingipRouteDto.setFloatingIpAddress(floatingIpToPluginDto.getFloatingIpAddress());
            deviceFloatingipRouteDto.setRxAverateLimit(floatingIpToPluginDto.getRxAverateLimit());
            deviceFloatingipRouteDto.setTxAverateLimit(floatingIpToPluginDto.getTxAverateLimit());
            deviceFloatingipRouteDtos.add(deviceFloatingipRouteDto);

            EipRateLimitDto eipRateLimitDto = new EipRateLimitDto();
            eipRateLimitDto.setDeviceIp(deviceExitRouteDto1.getDeviceIp());
            eipRateLimitDto.setFloatingIpAddress(floatingIpToPluginDto.getFloatingIpAddress());
            eipRateLimitDto.setPassword(deviceExitRouteDto1.getUserPassword());
            eipRateLimitDto.setUsername(deviceExitRouteDto1.getUserName());
            eipRateLimitDto.setPortNum(deviceExitRouteDto1.getPortNum());
            eipRateLimitDto.setRxAverateLimit(floatingIpToPluginDto.getRxAverateLimit());
            eipRateLimitDto.setTxAverateLimit(floatingIpToPluginDto.getTxAverateLimit());
            eipRateLimitDtos.add(eipRateLimitDto);
        });
        deviceFloatingipRouteMapper.insertDeviceFloatingipRoute(deviceFloatingipRouteDtos);

        //TODO 下发路由器带宽到agent
        eipRateLimitDtos.forEach(eipRateLimitDto -> routeService.createBandwidthToRouteAgent(eipRateLimitDto));
    }


    private void createDnatToVfw(FloatingIpToPluginDto floatingIpToPluginDto) {
        //1、获取主机所属的vpn-instance的id，如： 2jn9lr97r19f5p645sotugnglq
        String vpnInstanceId = floatingIpUtil.getVpnInstanceIdByFixedIpAddr(floatingIpToPluginDto.getFixedIpAddress());

        //2、根据vpnInstanceId获取虚墙信息
        VfwInfoDto vfwInfoDto = floatingIpUtil.getVfwInfo(vpnInstanceId);

        //2、并根据vpn-insantance-id和floatingipAddress 组合acl name，如：SDN_NAT_ACL_2jn9lr97r19f5p645sotugnglq_103_36_30_4
        String aclName = "UNI_NAT_ACL_" + vpnInstanceId + "_" + floatingIpToPluginDto.getFloatingIpAddress();

        //3、封装DeviceFloatingVfwDto
        DeviceFloatingVfwDto deviceFloatingVfwDto = new DeviceFloatingVfwDto();
        deviceFloatingVfwDto.setAclName(aclName);
        deviceFloatingVfwDto.setVfwMgrIp(vfwInfoDto.getMgrIp());
        deviceFloatingVfwDto.setVpnInstanceId(vpnInstanceId);
        deviceFloatingVfwDto.setFixedIpAddress(floatingIpToPluginDto.getFixedIpAddress());
        deviceFloatingVfwDto.setFloatingIpAddress(floatingIpToPluginDto.getFloatingIpAddress());
        deviceFloatingVfwDto.setType("dnat");

        //4、存device数据表
        deviceFloatingIpVfwMapper.insertDeviceFloatingIpVfw(deviceFloatingVfwDto);

        //5、下发防火墙
        vfwService.createDnatToAgent(vfwInfoDto, deviceFloatingVfwDto);
    }



    private void createDsnatToVfw(FloatingIpToPluginDto floatingIpToPluginDto) {
        //1、获取主机所属的vpn-instance的id，如： 2jn9lr97r19f5p645sotugnglq
        String vpnInstanceId = floatingIpUtil.getVpnInstanceIdByFixedIpAddr(floatingIpToPluginDto.getFixedIpAddress());
        //2、根据vpnInstanceId获取虚墙信息
        VfwInfoDto vfwInfoDto = floatingIpUtil.getVfwInfo(vpnInstanceId);
        //3、并根据vpn-insantance-id 组合acl name，如： SDN_NAT_ACL_2jn9lr97r19f5p645sotugnglq
        String aclName = "UNI_NAT_ACL_" + vpnInstanceId + "_" + floatingIpToPluginDto.getFloatingIpAddress();
        //TODO 4、获取不重复的address-group的id
        String addressGroupId = "";
        //5、根据vpn-insantance-id 组合address-group name,如：SDN_ADDR_2jn9lr97r19f5p645sotugnglq
        String addressGroupName = "UNI_NAT_GROUP_" + vpnInstanceId + "_" + addressGroupId;
        //6、存device库
        // 封装DeviceFloatingVfwDto
        DeviceFloatingVfwDto deviceFloatingVfwDto = new DeviceFloatingVfwDto();
        deviceFloatingVfwDto.setVfwMgrIp(vfwInfoDto.getMgrIp());
        deviceFloatingVfwDto.setVpnInstanceId(vpnInstanceId);
        deviceFloatingVfwDto.setType("dsnat");
        deviceFloatingVfwDto.setFloatingIpAddress(floatingIpToPluginDto.getFloatingIpAddress());
        deviceFloatingVfwDto.setFixedIpAddress(floatingIpToPluginDto.getFixedIpAddress());
        deviceFloatingVfwDto.setAclName(aclName);
        deviceFloatingVfwDto.setAddressGroupId(addressGroupId);
        deviceFloatingVfwDto.setAddressGroupName(addressGroupName);
        deviceFloatingIpVfwMapper.insertDeviceFloatingIpVfw(deviceFloatingVfwDto);


        List<DeviceFloatingVfwIpNumDto> deviceFloatingVfwIpNumDtos = new ArrayList<>();
        List<DeviceFloatingVfwSubnetDto> deviceFloatingVfwSubnetDtos = new ArrayList<>();
        DeviceFloatingVfwIpNumDto deviceFloatingVfwIpNumDto = new DeviceFloatingVfwIpNumDto();
        DeviceFloatingVfwSubnetDto deviceFloatingVfwSubnetDto = new DeviceFloatingVfwSubnetDto();


        //6、下发防火墙

    }


    private void createSnatToVfw(FloatingIpToPluginDto floatingIpToPluginDto) {
        //TODO 1、获取主机所属的vpn-instance的id，如： 2jn9lr97r19f5p645sotugnglq
        String vpnInstanceId = "";
        //2、根据vpnInstanceId获取虚墙信息
        VfwInfoDto vfwInfoDto = floatingIpUtil.getVfwInfo(vpnInstanceId);
        //3、并根据vpn-insantance-id 组合acl name，如： SDN_NAT_ACL_2jn9lr97r19f5p645sotugnglq
        String aclName = "UNI_NAT_ACL_" + vpnInstanceId + "_" + floatingIpToPluginDto.getFloatingIpAddress();
        //TODO 4、获取不重复的address-group的id
        String addressGroupId = "";
        //5、根据vpn-insantance-id 组合address-group name,如：SDN_ADDR_2jn9lr97r19f5p645sotugnglq
        String addressGroupName = "UNI_NAT_GROUP_" + vpnInstanceId + "_" + addressGroupId;
        //6、存device表
        // 封装DeviceFloatingVfwDto
        DeviceFloatingVfwDto deviceFloatingVfwDto = new DeviceFloatingVfwDto();
        deviceFloatingVfwDto.setType("snat");
        deviceFloatingVfwDto.setAclName(aclName);
        deviceFloatingVfwDto.setVfwMgrIp(vfwInfoDto.getMgrIp());
        deviceFloatingVfwDto.setAddressGroupId(addressGroupId);
        deviceFloatingVfwDto.setAddressGroupName(addressGroupName);
        deviceFloatingVfwDto.setVpnInstanceId(vpnInstanceId);
        deviceFloatingIpVfwMapper.insertDeviceFloatingIpVfw(deviceFloatingVfwDto);



        //7、下发防火墙
    }


    /**
     * @param floatingIpToPluginDto
     * @return
     */
    @Transactional
    public Boolean unbindFloatingIpToAgent(FloatingIpToPluginDto floatingIpToPluginDto){
        logger.info("FloatingipPluginService-unbindFloatingIpToAgent start-floatingIpToPluginDto:{}", JSON.toJSONString(floatingIpToPluginDto));
        //floatingIpToPluginDto
        if (floatingIpToPluginDto == null){
            logger.error("floatingIpToPluginDto is null");
            return false;
        }

        //校验不同类型 dnat、dsnat、snat 传入的参数
        switch (floatingIpToPluginDto.getType()) {
            case "dnat":
                if (floatingIpToPluginDto.getFloatingIpAddress() == null){
                    logger.error("dsnat:param is invalid!");
                    return false;
                }
                //删除dnat和带宽
                deleteDnatAndBandwidth(floatingIpToPluginDto);
                break;
            case "dsnat":
                boolean result = floatingIpToPluginDto.getFixedIpAddress() == null || floatingIpToPluginDto.getFloatingIpAddress() == null ||
                        floatingIpToPluginDto.getProtocal() == null || (floatingIpToPluginDto.getFixedIpNum() == null && floatingIpToPluginDto.getFloatingIpNum() != null) ||
                        (floatingIpToPluginDto.getFixedIpNum() != null && floatingIpToPluginDto.getFloatingIpNum() == null);
                if (result){
                    logger.error("dnat:param is invalid!");
                    return false;
                }

                //根据 floatingIpAddess、flaotingIpNum、fixedIpAddress、fixedIpNum删除数据

                //调用agent删除防火墙配置

                //如果数据表中还存在该公网Ip的数据，不用删除带宽配置，否则删除

                break;
            case "snat":
                if (CollectionUtils.isEmpty(floatingIpToPluginDto.getSubnetCidrs()) || floatingIpToPluginDto.getFloatingIpAddress() == null){
                    logger.error("snat:param is invalid!");
                    return false;
                }

                //根据floatingIpAddress和subnetCidr删除数据

                //调用agent删除防火墙配置

                //如果数据表中还存在该公网Ip的数据，不用删除带宽配置，否则删除

                break;
            default:
                break;
        }
        return true;
    }

    private void deleteDnatAndBandwidth(FloatingIpToPluginDto floatingIpToPluginDto) {
        logger.info("FloatingipPluginService-deleteDnatAndBandwidth-start:floatingIpToPluginDto:{}", JSON.toJSONString(floatingIpToPluginDto));
        //获取主机所属的vpn-instance的id，如： 2jn9lr97r19f5p645sotugnglq
        String vpnInstanceId = floatingIpUtil.getVpnInstanceIdByFixedIpAddr(floatingIpToPluginDto.getFixedIpAddress());
        //根据vpnInstanceId获取虚墙信息
        VfwInfoDto vfwInfoDto = floatingIpUtil.getVfwInfo(vpnInstanceId);
        //查询DeviceFloatingVfwDto
        DeviceFloatingVfwDto deviceFloatingVfwDto = deviceFloatingIpVfwMapper.queryDeviceFloatingIpVfwByAddress(floatingIpToPluginDto.getFloatingIpAddress());
        //根据floatingipAddress删除表
        deviceFloatingIpVfwMapper.deleteDeviceFloatingIpVfwByAddress(floatingIpToPluginDto.getFloatingIpAddress());
        //调用agent删除防火墙配置
        vfwService.deleteDnatToAgent(vfwInfoDto, deviceFloatingVfwDto);
        //删除带宽配置数据
        List<DeviceFloatingipRouteDto> deviceFloatingipRouteDtos = deviceFloatingipRouteMapper.queryDeviceFloatingipRouteByParam(null, floatingIpToPluginDto.getFloatingIpAddress());
        deviceFloatingipRouteMapper.deleteDeviceFloatingipRouteByAddress(floatingIpToPluginDto.getFloatingIpAddress());
        //TODO 调用agent 删除带宽
        List<DeviceExitRouteDto> deviceExitRouteDtos = deviceExitRouteMapper.queryExitRoute();
        List<EipRateLimitDto> eipRateLimitDtos = new ArrayList<>();
        deviceExitRouteDtos.forEach(deviceExitRouteDto -> {
            EipRateLimitDto eipRateLimitDto = new EipRateLimitDto();
            eipRateLimitDto.setDeviceIp(deviceExitRouteDto.getDeviceIp());
            eipRateLimitDto.setFloatingIpAddress(floatingIpToPluginDto.getFloatingIpAddress());
            eipRateLimitDto.setPassword(deviceExitRouteDto.getUserPassword());
            eipRateLimitDto.setUsername(deviceExitRouteDto.getUserName());
            eipRateLimitDto.setPortNum(deviceExitRouteDto.getPortNum());
            eipRateLimitDto.setRxAverateLimit(floatingIpToPluginDto.getRxAverateLimit());
            eipRateLimitDto.setTxAverateLimit(floatingIpToPluginDto.getTxAverateLimit());
            eipRateLimitDtos.add(eipRateLimitDto);
        });
        //TODO 下发路由器带宽到agent
        eipRateLimitDtos.forEach(eipRateLimitDto -> routeService.deleteBandwidthToRouteAgent(eipRateLimitDto));


    }
}
