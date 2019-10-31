package com.uca.network.extension.mapper.reservedip;

import com.uca.network.extension.mapper.reservedip.dto.ReservedIpDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/31
 */
public interface ReservedIpallocationsMapper {

    ReservedIpDto queryReservedIpByIpAddress(@Param("subnetId") String subnetId, @Param("ipAddress") String ipAddress);

    List<ReservedIpDto> queryReservedIpByAllocated(@Param("allocated") int allocated);

    void insertReservedIp(ReservedIpDto reservedIpDto);

    void deleteReservedIpByIpAddress(@Param("ipAddress") String ipAddress);

}
