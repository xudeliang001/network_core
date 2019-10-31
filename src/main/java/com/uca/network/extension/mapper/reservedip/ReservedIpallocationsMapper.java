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

    /**
     * @param subnetId
     * @param ipAddress
     * @return
     */
    ReservedIpDto queryReservedIpByIpAddress(@Param("subnetId") String subnetId, @Param("ipAddress") String ipAddress);

    /**
     * @param allocated
     * @return
     */
    List<ReservedIpDto> queryReservedIpByAllocated(@Param("allocated") int allocated);

    /**
     * @param reservedIpDto
     */
    void insertReservedIp(ReservedIpDto reservedIpDto);

    /**
     * @param ipAddress
     */
    void deleteReservedIpByIpAddress(@Param("ipAddress") String ipAddress);

}
