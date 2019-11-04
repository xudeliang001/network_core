package com.uca.network.core.router.controller;

import com.uca.network.common.utils.RestfulEntity;
import com.uca.network.core.router.controller.model.RouterReq;
import com.uca.network.core.router.service.RouterService;
import com.uca.network.core.router.vo.RouterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1.0/routers")
public class RouterController {
    private static final Logger logger = LoggerFactory.getLogger(RouterController.class);

    @Autowired
    private RouterService routerService;

    @PostMapping("create")
    public RestfulEntity createRouter(@RequestBody  RouterReq routerReq) {
        RouterVo result = routerService.createRouter(routerReq);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("router", result);
        return RestfulEntity.getSuccess(resultMap);
    }

    @GetMapping("/{routerId}/query")
    public RestfulEntity querySubnetDetail(String tenantId, @PathVariable String routerId) {
        RouterVo result = routerService.queryRouterDetail(tenantId, routerId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("router", result);
        return RestfulEntity.getSuccess(resultMap);
    }

    @GetMapping("/query")
    public RestfulEntity queryRouters(@RequestParam String tenantId) {
        List<RouterVo> result = routerService.queryRouters(tenantId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("routers", result);
        return RestfulEntity.getSuccess(resultMap);
    }

    @PostMapping("/{routerId}/delete")
    public RestfulEntity deleteRouter(String tenantId, String routerId) {
        routerService.deleteRouter(tenantId, routerId);
        return RestfulEntity.getSuccess(true);
    }

}
