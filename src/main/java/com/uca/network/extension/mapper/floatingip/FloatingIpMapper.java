package com.uca.network.extension.mapper.floatingip;

import com.uca.network.extension.mapper.floatingip.dto.FloatingIpDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FloatingIpMapper {

    List<FloatingIpDto> queryFloatingIpsByParam(@Param("projectId") String projectId, @Param("ipAddress") String ipAddress);

    List<FloatingIpDto> queryAllFloatingIps();
}
