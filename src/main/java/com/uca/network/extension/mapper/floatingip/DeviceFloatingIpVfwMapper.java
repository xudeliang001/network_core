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
    String insertFloatingIpVfw(DeviceFloatingVfwDto floatingVfwDto);

    /**
     * @param floatingVfwDtos
     * @return
     */
    List<String> insertFloatingIpVfws(List<DeviceFloatingVfwDto> floatingVfwDtos);

    /**
     * @param floatingIpAddress
     * @return
     */
    DeviceFloatingVfwDto queryFloatingIpVfwByAddress(@Param("floatingIpAddress") String floatingIpAddress);

    /**
     * @param floatingIpAddress
     */
    void deleteFloatingIpVfwByAddress(@Param("floatingIpAddress") String floatingIpAddress);

}
