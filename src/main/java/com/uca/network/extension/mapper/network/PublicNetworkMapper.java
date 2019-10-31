package com.uca.network.extension.mapper.network;

import com.uca.network.extension.mapper.network.dto.PublicNetworkDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PublicNetworkMapper {

    public List<PublicNetworkDto> queryNetworksByParam(@Param("networkId") String networkId);

}
