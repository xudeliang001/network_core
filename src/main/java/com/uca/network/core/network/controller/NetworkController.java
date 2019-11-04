package com.uca.network.core.network.controller;


import com.uca.network.common.utils.RestfulEntity;
import com.uca.network.core.network.controller.model.NetworkReq;
import com.uca.network.core.network.service.NetworkService;
import com.uca.network.core.network.vo.NetworkVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController(value = "coreNetworkController")
@RequestMapping(value = "/v1.0/networks")
public class NetworkController {

    private static final Logger logger = LoggerFactory.getLogger(NetworkController.class);
    @Autowired
    private NetworkService networkService;

    @PostMapping("create")
    public RestfulEntity createNetwork(NetworkReq networkReq) {
        NetworkVo result = networkService.createNetwork(networkReq);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("network", result);
        return RestfulEntity.getSuccess(resultMap);
    }

    @GetMapping("/{networkId}/query")
    public RestfulEntity queryNetworkDetail(String tenantId, @PathVariable String networkId) {
        NetworkVo result = networkService.queryNetworkDetail(tenantId, networkId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("network", result);
        return RestfulEntity.getSuccess(resultMap);
    }

    @GetMapping("/query")
    public RestfulEntity queryNetworks(String tenantId) {
        List<NetworkVo> result = networkService.queryNetworks(tenantId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("networks", result);
        return RestfulEntity.getSuccess(networkService.queryNetworks(tenantId));
    }

    @PostMapping("/{networkId}/delete")
    public RestfulEntity deleteSubent(String tenantId, @PathVariable String networkId) {
        networkService.deleteNetwork(tenantId, networkId);
        return RestfulEntity.getSuccess(true);
    }
}
