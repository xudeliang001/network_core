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
public class SnatDto {
    private String ip;
    private String username;
    private String password;
    private AclDto aclDto;
    @JsonProperty("address_group")
    @JSONField(name = "address_group")
    private AddressGroupDto addressGroupDto;
    @JsonProperty("nat_outbound")
    @JSONField(name = "nat_outbound")
    private NatOutboundDto natOutbound;

}
