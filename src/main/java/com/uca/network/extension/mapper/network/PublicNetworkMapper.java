package com.uca.network.extension.mapper.network;

import com.uca.network.extension.mapper.network.dto.PublicNetworkDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
public interface PublicNetworkMapper {

    /**
     * @param networkId
     * @return
     */
    List<PublicNetworkDto> queryNetworksByParam(@Param("networkId") String networkId);

    /**
     * @param publicNetworkDto
     */
    void insertPublicNetwork(PublicNetworkDto publicNetworkDto);

    /**
     * @param id
     */
    void deletePublicNetworkById(@Param("id") String id);

}
