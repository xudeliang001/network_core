package com.uca.network.core.mapper.ipallocation;

import com.uca.network.core.mapper.ipallocation.dto.IpAllocationsDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IpallocationsMapper {

    public List<IpAllocationsDto> queryIpAllocationsByParam(@Param("portIds") List<String> portIds,
                                                            @Param("subnetIds") List<String> subnetIds,
                                                            @Param("networkIds") List<String> networkIds);

    public int insertIpAllocations(IpAllocationsDto ipAllocationsDto);
    

    public void deleteIpAllocationsByPortId(@Param("portId") String portId);


}
