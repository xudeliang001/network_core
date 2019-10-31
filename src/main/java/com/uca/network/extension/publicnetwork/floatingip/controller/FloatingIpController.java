package com.uca.network.extension.publicnetwork.floatingip.controller;

import com.uca.network.common.utils.RestfulEntity;
import com.uca.network.extension.publicnetwork.floatingip.vo.FloatingIpCreateReqVo;
import com.uca.network.extension.publicnetwork.floatingip.vo.FloatingIpResVo;
import com.uca.network.extension.publicnetwork.floatingip.vo.FloatingIpUpdateReqVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/10/28
 */
@RestController
@RequestMapping("/v1.0/floatingips")
public class FloatingIpController {

    @ApiOperation(value = "创建公网IP")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public RestfulEntity<FloatingIpResVo> createFloatingIp(HttpServletRequest request, @RequestBody FloatingIpCreateReqVo floatingIpCreateReqVo){

        return null;
    }

    @ApiOperation(value = "修改公网IP")
    @RequestMapping(value = "/{floatingipId}/update", method = RequestMethod.POST)
    public RestfulEntity<FloatingIpResVo> updateFloatingIp(HttpServletRequest request, @PathVariable String floatingipId,
                                                           @RequestBody FloatingIpUpdateReqVo floatingIpUpdateReqVo){

        return null;
    }


    @ApiOperation(value = "删除公网IP")
    @RequestMapping(value = "/floatingipId}/delete", method = RequestMethod.POST)
    public RestfulEntity<String> deleteFloatingIp(HttpServletRequest request, @PathVariable String floatingipId){

        return null;
    }


    @ApiOperation(value = "根据floatingipId查询公网IP")
    @RequestMapping(value = "/{floatingipId}/query", method = RequestMethod.POST)
    public RestfulEntity<FloatingIpResVo> queryFloatingIpById(HttpServletRequest request, @PathVariable String floatingipId){

        return null;
    }


    @ApiOperation(value = "查询所有公网IP")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public RestfulEntity<FloatingIpResVo> queryFloatingIps(HttpServletRequest request){

        return null;
    }

}

