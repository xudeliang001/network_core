package com.uca.network.extension.plugin.floatingip.service;

import com.uca.network.extension.mapper.floatingip.DeviceFloatingIpVfwMapper;
import com.uca.network.extension.mapper.floatingip.dto.DeviceFloatingVfwDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
@Service
public class FloatingipPluginService {
    private static final Logger logger = LoggerFactory.getLogger(FloatingipPluginService.class);

    @Autowired
    private DeviceFloatingIpVfwMapper floatingIpVfwMapper;

    /**
     * @param floatingVfwDto
     * @return
     */
    public Boolean updateFloatingIpToAgent(DeviceFloatingVfwDto floatingVfwDto){

        //校验floatingVfwDto
        if (floatingVfwDto == null){
            logger.error("floatingVfwDto is null");
            return false;
        }

        //校验不同类型 dnat、dsnat、snat
        switch (floatingVfwDto.getType()) {
            case "dsnat":

                break;
            case "dnat":

                break;
            case "snat":

                break;
            default:
                break;
        }



        //存实际网络数据
        try {
            floatingIpVfwMapper.insertFloatingIpVfw(floatingVfwDto);
        } catch (Exception e){

        }


        //下发到agent


        return true;
    }
}
