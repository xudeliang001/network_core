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
public class AddressGroupDto {
    @JSONField(name = "groupname")
    @JsonProperty("groupname")
    private String groupName;
    @JSONField(name = "groupindex")
    @JsonProperty("groupindex")
    private String groupIndex;
    @JSONField(name = "start_eip")
    @JsonProperty("start_eip")
    private String startEip;
    @JSONField(name = "end_eip")
    @JsonProperty("end_eip")
    private String endEip;
}
