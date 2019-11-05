package com.uca.network.extension.plugin.floatingip.agent.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/4
 */
@Data
public class NatStaticDto {
    @JsonProperty("interface")
    @JSONField(name = "interface")
    private String interfaceName;
    private String aclname;
    private String ip;
    private String eip;
    private String vrf;
    @JsonProperty("external_vrf")
    @JSONField(name = "external_vrf")
    private String externalVrf;
}
