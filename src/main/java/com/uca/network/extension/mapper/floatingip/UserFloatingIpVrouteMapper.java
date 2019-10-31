package com.uca.network.extension.mapper.floatingip;

import com.uca.network.extension.mapper.floatingip.dto.UserFloatingIpVrouteDto;
import org.apache.ibatis.annotations.Param;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
public interface UserFloatingIpVrouteMapper {

    /**
     * @param userFloatingIpVrouteDto
     */
    void insertUserFloatingIpVroute(UserFloatingIpVrouteDto userFloatingIpVrouteDto);

    /**
     * @param floatingIpId
     */
    void deleteUserFloatingIpVrouteById(@Param("floatingIpId") String floatingIpId);

    /**
     * @param floatingIpId
     * @return
     */
    UserFloatingIpVrouteDto queryUserFloatingIpVrouteById(@Param("floatingIpId") String floatingIpId);
}
