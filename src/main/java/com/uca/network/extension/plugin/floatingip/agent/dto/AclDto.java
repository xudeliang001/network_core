package com.uca.network.extension.plugin.floatingip.agent.dto;

import lombok.Data;

import java.util.List;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/4
 */
@Data
public class AclDto {
    private String aclname;
    private String vrf;
    private List<RuleDto> rules;
}
