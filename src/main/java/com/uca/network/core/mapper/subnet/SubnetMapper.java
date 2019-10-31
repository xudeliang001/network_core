package com.uca.network.core.mapper.subnet;


import com.uca.network.core.mapper.subnet.dto.SubnetDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SubnetMapper {

    public List<SubnetDto> querySubnetsByParam(@Param("tenantId") String projectId, @Param("networkId") String networkId,
                                               @Param("networkIds") List<String> networkIds,
                                               @Param("ids") List<String> ids,
                                               @Param("id")String id);

    public int insertSubent(SubnetDto subnetDto);


    public void deleteSubnetByParam(@Param("param") Map<String, Object> param);
}
