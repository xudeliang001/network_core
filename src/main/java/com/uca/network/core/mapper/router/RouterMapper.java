package com.uca.network.core.mapper.router;

import com.uca.network.core.mapper.router.dto.RouterDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RouterMapper {

    public List<RouterDto> queryRoutersByParam(@Param("tenantId") String tenantId,
                                               @Param("routerId") String routerId,
                                               @Param("routerIds") List<String> routerIds);

    public int deleteRouterById(@Param("id") String routerId);


    public void insertRouter(RouterDto routerDto);

}
