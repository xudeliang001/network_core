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
public class NatOutboundDto {
    @JSONField(name = "interface")
    @JsonProperty("interface")
    private String interfaceName;
    @JSONField(name = "aclname")
    @JsonProperty("aclname")
    private String aclName;
    @JSONField(name = "groupname")
    @JsonProperty("groupname")
    private String groupName;
    @JSONField(name = "exteranl_vrf")
    @JsonProperty("exteranl_vrf")
    private String externalVrf;
}
