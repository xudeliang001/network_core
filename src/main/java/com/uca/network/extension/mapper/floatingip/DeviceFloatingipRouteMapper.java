package com.uca.network.extension.mapper.floatingip;

import com.uca.network.extension.mapper.floatingip.dto.DeviceFloatingipRouteDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
public interface DeviceFloatingipRouteMapper {

    /**
     * @param deviceIp
     * @param floatingIpAddress
     * @return
     */
    List<DeviceFloatingipRouteDto> queryDeviceFloatingipRouteByParam(@Param("deviceIp") String deviceIp, @Param("floatingIpAddress") String floatingIpAddress);

    /**
     * @param deviceFloatingipRouteDtos
     * @return
     */
    List<String> insertDeviceFloatingipRoute(List<DeviceFloatingipRouteDto> deviceFloatingipRouteDtos);

}
