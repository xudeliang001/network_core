package com.uca.network.extension.plugin.floatingip.service;

import com.alibaba.fastjson.JSON;
import com.uca.network.extension.mapper.floatingip.DeviceExitRouteMapper;
import com.uca.network.extension.mapper.floatingip.DeviceFloatingIpVfwMapper;
import com.uca.network.extension.mapper.floatingip.DeviceFloatingipRouteMapper;
import com.uca.network.extension.mapper.floatingip.dto.DeviceExitRouteDto;
import com.uca.network.extension.mapper.floatingip.dto.DeviceFloatingVfwDto;
import com.uca.network.extension.mapper.floatingip.dto.DeviceFloatingipRouteDto;
import com.uca.network.extension.plugin.floatingip.dto.FloatingIpToPluginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    /**
     * @param floatingIpToPluginDto
     * @return
     */
    public Boolean bindFloatingIpToAgent(FloatingIpToPluginDto floatingIpToPluginDto){
        logger.info("FloatingipPluginService-bindFloatingIpToAgent start-floatingIpToPluginDto:{}", JSON.toJSONString(floatingIpToPluginDto));
        //floatingIpToPluginDto
        if (floatingIpToPluginDto == null){
            logger.error("floatingIpToPluginDto is null");
            return false;
        }

        //校验不同类型 dnat、dsnat、snat 传入的参数
        //Boolean result = checkFloatingIpToPluginParam(floatingIpToPluginDto);
        //需要存储实际网络模型vfw
        DeviceFloatingVfwDto deviceFloatingVfwDto = new DeviceFloatingVfwDto();

        switch (floatingIpToPluginDto.getType()) {
            case "dnat":
                if (floatingIpToPluginDto.getFixedIpAddress() == null || floatingIpToPluginDto.getFloatingIpAddress() == null){
                    logger.error("dsnat:param is invalid!");
                    return false;
                }
                //组装存库数据和下发agent数据
                //1、获取主机所属的vpn-instance的id，如： 2jn9lr97r19f5p645sotugnglq

                //2、并根据vpn-insantance-id和floatingipAddress 组合acl name，如：SDN_NAT_ACL_2jn9lr97r19f5p645sotugnglq_103_36_30_4

                //3、封装DeviceFloatingVfwDto

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
                //1、获取主机所属的vpn-instance的id，如： 2jn9lr97r19f5p645sotugnglq

                //2、并根据vpn-insantance-id 组合acl name，如： SDN_NAT_ACL_2jn9lr97r19f5p645sotugnglq

                //3、获取不重复的address-group的id

                //4、根据vpn-insantance-id 组合address-group name,如：SDN_ADDR_2jn9lr97r19f5p645sotugnglq

                //5、封装DeviceFloatingVfwDto

                break;

            case "snat":
                if (CollectionUtils.isEmpty(floatingIpToPluginDto.getSubnetCidrs()) || floatingIpToPluginDto.getFloatingIpAddress() == null){
                    logger.error("snat:param is invalid!");
                    return false;
                }

                //组装存库数据和下发agent数据
                //1、获取主机所属的vpn-instance的id，如： 2jn9lr97r19f5p645sotugnglq

                //2、并根据vpn-insantance-id 组合acl name，如： SDN_NAT_ACL_2jn9lr97r19f5p645sotugnglq

                //3、获取不重复的address-group的id

                //4、根据vpn-insantance-id 组合address-group name,如：SDN_ADDR_2jn9lr97r19f5p645sotugnglq

                //5、封装DeviceFloatingVfwDto

                break;
            default:
                break;
        }

        //封装带宽数据
        List<DeviceFloatingipRouteDto> deviceFloatingipRouteDtos = new ArrayList<>();
        List<DeviceExitRouteDto> deviceExitRouteDtos = deviceExitRouteMapper.queryExitRoute();
        deviceExitRouteDtos.forEach(deviceExitRouteDto1 -> {
            DeviceFloatingipRouteDto deviceFloatingipRouteDto = new DeviceFloatingipRouteDto();
            deviceFloatingipRouteDto.setDeviceIp(deviceExitRouteDto1.getDeviceIp());
            deviceFloatingipRouteDto.setFloatingIpAddress(floatingIpToPluginDto.getFloatingIpAddress());
            deviceFloatingipRouteDto.setRxAverateLimit(floatingIpToPluginDto.getRxAverateLimit());
            deviceFloatingipRouteDto.setTxAverateLimit(floatingIpToPluginDto.getTxAverateLimit());
            deviceFloatingipRouteDtos.add(deviceFloatingipRouteDto);
        });


        //存实际网络数据
        try {
            deviceFloatingIpVfwMapper.insertFloatingIpVfw(deviceFloatingVfwDto);
            deviceFloatingipRouteMapper.insertDeviceFloatingipRoute(deviceFloatingipRouteDtos);
        } catch (Exception e){

        }


        //下发到agent
            //下发防火墙

            //下发路由器带宽


        return true;
    }



    /**
     * @param floatingIpToPluginDto
     * @return
     */
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
                //根据floatingip删除表
                deviceFloatingIpVfwMapper.deleteFloatingIpVfwByAddress(floatingIpToPluginDto.getFloatingIpAddress());
                //调用agent删除防火墙配置

                //删除带宽配置

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
}
