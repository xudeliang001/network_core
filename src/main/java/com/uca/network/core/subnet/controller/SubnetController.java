package com.uca.network.core.subnet.controller;


import com.uca.network.common.utils.RestfulEntity;
import com.uca.network.core.subnet.controller.model.SubnetReq;
import com.uca.network.core.subnet.service.SubnetService;
import com.uca.network.core.subnet.vo.SubnetVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController(value = "coreSubentController")
@RequestMapping(value = "/v1.0/subnets")
public class SubnetController {

    private static final Logger logger = LoggerFactory.getLogger(SubnetController.class);

    @Autowired
    private SubnetService subnetService;

    @PostMapping("create")
    public RestfulEntity createSubnet(SubnetReq subnetReq) {
        SubnetVo result = subnetService.createSubnet(subnetReq);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("subnet", result);
        return RestfulEntity.getSuccess(resultMap);
    }

    @GetMapping("/{subnetId}/query")
    public RestfulEntity querySubnetDetail(String tenantId, @PathVariable String subnetId) {
        SubnetVo result = subnetService.querySubnetDetail(tenantId, subnetId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("subnet", result);
        return RestfulEntity.getSuccess(resultMap);
    }

    @GetMapping("/query")
    public RestfulEntity querySubnets(@RequestParam String tenantId, @RequestParam(required = false) String networkId) {
        List<SubnetVo> result = subnetService.querySubnets(tenantId, networkId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("subnets", result);
        return RestfulEntity.getSuccess(resultMap);
    }

    @PostMapping("/{subnetId}/delete")
    public RestfulEntity deleteSubent(String tenantId, @PathVariable  String subnetId) {
        subnetService.deleteSubent(tenantId, subnetId, null);
        return RestfulEntity.getSuccess(true);
    }

}
