package com.uca.network.core.mapper.ports;


import com.uca.network.core.mapper.ports.dto.PortsDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PortsMapper {

    public List<PortsDto> queryPortsByParam(@Param("deviceId") String deviceId, @Param("deviceOwner") String deviceOwner,
                                            @Param("tenantId") String tenantId, @Param("networkId") String networkId,
                                            @Param("networkIds") List<String> networkIds,
                                            @Param("deviceIds") List<String> deviceIds,
                                            @Param("ids") List<String> ids);

}
