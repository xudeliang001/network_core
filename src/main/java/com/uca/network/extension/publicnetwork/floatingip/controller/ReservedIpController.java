package com.uca.network.extension.publicnetwork.floatingip.controller;

import com.uca.network.common.utils.RestfulEntity;
import com.uca.network.extension.publicnetwork.floatingip.vo.ReservedIpCheckReqVo;
import com.uca.network.extension.publicnetwork.floatingip.vo.ReservedIpCreateReqVo;
import com.uca.network.extension.publicnetwork.floatingip.vo.ReservedIpResVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/28
 */
@RestController
@RequestMapping("/v1.0/floatingips/reserved_ips")
public class ReservedIpController {

    @ApiOperation(value = "校验预留IP")
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public RestfulEntity<String> checkReservedIps(HttpServletRequest request, @RequestBody ReservedIpCheckReqVo reservedIpCheckReqVo){

        return null;
    }

    @ApiOperation(value = "创建预留IP")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RestfulEntity<ReservedIpResVo> createReservedIps(HttpServletRequest request, @RequestBody ReservedIpCreateReqVo reservedIpCreateReqVo){

        return null;
    }

    @ApiOperation(value = "查询预留IP")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public RestfulEntity<ReservedIpResVo> queryReservedIps(HttpServletRequest request){

        return null;
    }
}
