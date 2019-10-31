package com.uca.network.core.mapper.ipallocationpools;

import com.uca.network.core.mapper.ipallocationpools.dto.IpaAllocationpoolsDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IpallocationPoolsMapper {

    List<IpaAllocationpoolsDto> queryIpAllocationPoolsByParam(@Param("subnetIds") List<String> subnetIds);

    public int insertIpAllocationPools(IpaAllocationpoolsDto ipaAllocationpoolsDto);

    public void deleteIpAllocationPoolsByParam(@Param("param") Map<String,Object> map);

}
