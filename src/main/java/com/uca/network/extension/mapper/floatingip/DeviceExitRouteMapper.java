package com.uca.network.extension.mapper.floatingip;

import com.uca.network.extension.mapper.floatingip.dto.DeviceExitRouteDto;

import java.util.List;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
public interface DeviceExitRouteMapper {

    /**
     * @return
     */
    List<DeviceExitRouteDto> queryExitRoute();
}
