package com.uca.network.extension.plugin.floatingip.dto;

import lombok.Data;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/4
 */
@Data
public class VfwInfoDto {
    private String mgrIp;
    private String userName;
    private String userPwd;
    private String deviceId;
    private String contextName;
    private String contextId;
}
