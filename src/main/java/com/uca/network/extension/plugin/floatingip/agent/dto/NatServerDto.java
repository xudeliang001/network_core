package com.uca.network.extension.plugin.floatingip.agent.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/4
 */
@Data
public class NatServerDto {
    private String vrf;
    @JSONField(name = "exteranl_vrf")
    @JsonProperty("exteranl_vrf")
    private String externalVrf;
    @JSONField(name = "global_configs")
    @JsonProperty("global_configs")
    List<GlobalConfigDto> globalConfigs;
}
