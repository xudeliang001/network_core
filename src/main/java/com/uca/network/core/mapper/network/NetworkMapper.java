package com.uca.network.core.mapper.network;

import com.uca.network.core.mapper.network.dto.NetworkDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NetworkMapper {

    public List<NetworkDto> queryNetworksByParam(@Param("tenantId") String tenandId,
                                                 @Param("networkId") String networkId,
                                                 @Param("networkIds") List<String> networkIds);

    public int insertNetwork(NetworkDto networkDto);

    public void deleteNetworkById(@Param("id") String networkId);

}
