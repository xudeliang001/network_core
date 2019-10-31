package com.uca.network.extension.mapper.floatingip;

import com.uca.network.extension.mapper.floatingip.dto.FloatingVfwDto;
import org.apache.ibatis.annotations.Param;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
public interface FloatingIpVfwMapper {

    /**
     * @param floatingVfwDto
     * @return
     */
    String insertFloatingIpVfw(FloatingVfwDto floatingVfwDto);

    /**
     * @param floatingIpAddress
     * @return
     */
    FloatingVfwDto queryFloatingIpVfwByAddress(@Param("floatingIpAddress") String floatingIpAddress);

    /**
     * @param floatingIpAddress
     */
    void deleteFloatingIpVfwByAddress(@Param("floatingIpAddress") String floatingIpAddress);

}
