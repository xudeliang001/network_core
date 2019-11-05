package com.uca.network.extension.plugin.floatingip.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uca.network.common.utils.RestClient;
import com.uca.network.extension.mapper.floatingip.dto.DeviceExitRouteDto;
import com.uca.network.extension.mapper.floatingip.dto.DeviceFloatingipRouteDto;
import com.uca.network.extension.plugin.floatingip.agent.dto.EipRateLimitDto;
import com.uca.network.extension.plugin.floatingip.service.FloatingipPluginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/4
 */
@Service
public class RouteService {
    private static final Logger logger = LoggerFactory.getLogger(RouteService.class);
    @Autowired
    private RestClient restClient;

    public void createBandwidthToRouteAgent(EipRateLimitDto eipRateLimitDto){
        try {
            logger.info("createBandwidthToRouteAgent-start-eipRateLimitDto:{}", JSONObject.toJSONString(eipRateLimitDto));
            //调用脚本下发
            String url = "/eip/createlimit";

            try {
                logger.info("createBandwidthToRouteAgent --> start changing eip_rateLimit, eipRateLimitDto: {}", JSON.toJSONString(eipRateLimitDto));
                String result = restClient.putWithToken(url, null, eipRateLimitDto, String.class);
                logger.info("createBandwidthToRouteAgent --> change eip_rateLimit completed, result: {}", result);

                int retCode = JSONObject.parseObject(result).getInteger("rc");
                String msg = JSONObject.parseObject(result).getString("msg");

                if (retCode != 0) {
                    logger.error("createBandwidthToRouteAgent --> change eip_rateLimit failed, retCode:{},msg:{}", retCode, msg);
                } else {
                }
            } catch (Exception e) {
                logger.error("createBandwidthToRouteAgent --> change eip_rateLimit failed, errorMsg: {}", e.getMessage(), e);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void deleteBandwidthToRouteAgent(EipRateLimitDto eipRateLimitDto){
        try {
            logger.info("deleteBandwidthToRouteAgent-starteipRateLimitDto:{}", JSONObject.toJSONString(eipRateLimitDto));
            //调用脚本下发
            String url = "/eip/deletelimit";

            try {
                String result = restClient.putWithToken(url, null, eipRateLimitDto, String.class);
                logger.info("deleteBandwidthToRouteAgent --> delete eip_rateLimit completed, result: {}", result);

                int retCode = JSONObject.parseObject(result).getInteger("rc");
                String msg = JSONObject.parseObject(result).getString("msg");

                if (retCode != 0) {
                    logger.error("deleteBandwidthToRouteAgent --> delete eip_rateLimit failed, retCode:{},msg:{}", retCode, msg);
                } else {
                }
            } catch (Exception e) {
                logger.error("deleteBandwidthToRouteAgent --> delete eip_rateLimit failed, errorMsg: {}", e.getMessage(), e);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }


}
