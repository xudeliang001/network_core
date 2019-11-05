package com.uca.network.extension.plugin.floatingip.agent.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EipRateLimitDto {

    @JSONField(name = "floating_ip_address")
    @JsonProperty("floating_ip_address")
    private String floatingIpAddress;

    @JSONField(name = "tx_averateLimit")
    @JsonProperty("tx_averateLimit")
    private String txAverateLimit;

    @JSONField(name = "rx_averateLimit")
    @JsonProperty("rx_averateLimit")
    private String rxAverateLimit;

    @JSONField(name = "device_ip")
    @JsonProperty("device_ip")
    private String deviceIp;

    @JSONField(name = "port_num")
    @JsonProperty("port_num")
    private String portNum;
    private String username;
    private String password;

}
