package com.uca.network.core.router.service;

import com.uca.network.core.router.controller.model.RouterReq;
import com.uca.network.core.router.vo.RouterVo;
import com.uca.network.core.subnet.controller.model.SubnetReq;
import com.uca.network.core.subnet.vo.SubnetVo;

import java.util.List;

public interface RouterService {

    public RouterVo createRouter(RouterReq routerReq);

    public void deleteRouter(String tenantId,String routerId);

    public List<RouterVo> queryRouters(String tenantId);

    public RouterVo queryRouterDetail(String tenantId, String routerId);
}
