package com.uca.network.extension.mapper.subnet;



import com.uca.network.extension.mapper.subnet.dto.PublicSubnetDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PublicSubnetMapper {

    public List<PublicSubnetDto> querySubnetsByParam(@Param("networkId") String networkId,
                                                     @Param("networkIds") List<String> networkIds,
                                                     @Param("ids") List<String> ids);


}
