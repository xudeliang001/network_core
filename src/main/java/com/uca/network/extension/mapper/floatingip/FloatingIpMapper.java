package com.uca.network.extension.mapper.floatingip;

import com.uca.network.extension.mapper.floatingip.dto.FloatingIpDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface FloatingIpMapper {

    List<FloatingIpDto> queryFloatingIpsByParam(@Param("tenantId") String tenantId, @Param("ipAddress") String ipAddress);

    List<FloatingIpDto> queryAllFloatingIps();

    void insertFloatingIps(FloatingIpDto floatingIpDto);

    void updateFloatingIps(@Param("fixedIpAddress") String fixedIpAddress, @Param("fixedPortId") String fixedPortId, @Param("routerId") String routerId,
                           @Param("lastKnownRouterId") String lastKnownRouterId, @Param("updatedAt") Date updatedAt, @Param("txAverateLimit") String txAverateLimit,
                           @Param("rxAverateLimit") String rxAverateLimit,  @Param("id") String id);
}
