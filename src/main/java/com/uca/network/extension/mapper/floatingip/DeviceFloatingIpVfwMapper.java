package com.uca.network.extension.mapper.floatingip;

import com.uca.network.extension.mapper.floatingip.dto.DeviceFloatingVfwDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
public interface DeviceFloatingIpVfwMapper {

    /**
     * @param floatingVfwDto
     * @return
     */
    String insertDeviceFloatingIpVfw(DeviceFloatingVfwDto floatingVfwDto);


    /**
     * @param floatingIpAddress
     * @return
     */
    DeviceFloatingVfwDto queryDeviceFloatingIpVfwByAddress(@Param("floatingIpAddress") String floatingIpAddress);

    /**
     * @param floatingIpAddress
     */
    void deleteDeviceFloatingIpVfwByAddress(@Param("floatingIpAddress") String floatingIpAddress);

    /**
     * @param floatingIpAddress
     */
    void deleteDeviceFloatingIpVfwIpNumByAddress(@Param("floatingIpAddress") String floatingIpAddress);

    /**
     * @param floatingIpAddress
     */
    void deleteDeviceFloatingIpVfwSubnetByAddress(@Param("floatingIpAddress") String floatingIpAddress);

}
