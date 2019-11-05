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
public class RuleDto {
    @JSONField(name = "src_cidr")
    @JsonProperty("src_cidr")
    private String srcCidr;
    @JSONField(name = "dst_cidr")
    @JsonProperty("dst_cidr")
    private String dstCidr;
}
