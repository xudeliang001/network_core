package com.uca.network.extension.plugin.floatingip.agent.dto;

import lombok.Data;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/4
 */
@Data
public class DnatDto {
    private String ip;
    private String username;
    private String password;
    private AclDto aclDto;
    private NatStaticDto natStaticDto;
}
