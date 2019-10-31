package com.uca.network.extension.mapper.subnet;



import com.uca.network.extension.mapper.subnet.dto.PublicSubnetDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
public interface PublicSubnetMapper {

    /**
     * @param networkId
     * @param networkIds
     * @param ids
     * @return
     */
    List<PublicSubnetDto> querySubnetsByParam(@Param("networkId") String networkId,
                                                     @Param("networkIds") List<String> networkIds,
                                                     @Param("ids") List<String> ids);

    /**
     * @param publicSubnetDto
     */
    void insertPublicSubnet(PublicSubnetDto publicSubnetDto);

    /**
     * @param id
     */
    void deletePublicSubnetById(@Param("id") String id);


}
