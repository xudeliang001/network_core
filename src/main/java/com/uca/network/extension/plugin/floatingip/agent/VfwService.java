package com.uca.network.extension.plugin.floatingip.agent;

import com.alibaba.fastjson.JSONObject;
import com.uca.network.common.config.ServerConfig;
import com.uca.network.extension.mapper.floatingip.dto.DeviceFloatingVfwDto;
import com.uca.network.extension.plugin.floatingip.agent.client.VfwClient;
import com.uca.network.extension.plugin.floatingip.agent.dto.AclDto;
import com.uca.network.extension.plugin.floatingip.agent.dto.DnatDto;
import com.uca.network.extension.plugin.floatingip.agent.dto.NatStaticDto;
import com.uca.network.extension.plugin.floatingip.dto.VfwInfoDto;
import com.uca.network.extension.plugin.floatingip.util.FloatingIpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/4
 */
@Service
public class VfwService {
    private static final Logger logger = LoggerFactory.getLogger(VfwService.class);
    @Autowired
    private FloatingIpUtil floatingIpUtil;
    @Autowired
    private VfwClient vfwClient;
    @Autowired
    private ServerConfig serverConfig;

    public void createDnatToAgent(VfwInfoDto vfwInfoDto, DeviceFloatingVfwDto deviceFloatingVfwDto){
        logger.info("VfwService-createDnatToAgentp-start,vfwInfoDto:{},deviceFloatingVfwDto:{}", JSONObject.toJSONString(vfwInfoDto),
                JSONObject.toJSONString(deviceFloatingVfwDto));
        DnatDto createDnatDto = convertToDnatDto(vfwInfoDto, deviceFloatingVfwDto);
        //PUT /eip/creatednat
        vfwClient.createDnat(serverConfig.getExtensionAgent(), JSONObject.toJSONString(createDnatDto));

    }

    public void deleteDnatToAgent(VfwInfoDto vfwInfoDto, DeviceFloatingVfwDto deviceFloatingVfwDto){
        logger.info("VfwService-deleteDnatToAgent-start,vfwInfoDto:{},deviceFloatingVfwDto:{}", JSONObject.toJSONString(vfwInfoDto),
                JSONObject.toJSONString(deviceFloatingVfwDto));
        DnatDto deleteDnatDto = convertToDnatDto(vfwInfoDto, deviceFloatingVfwDto);
        //PUT /eip/deletednat
        vfwClient.deleteDnat(serverConfig.getExtensionAgent(), JSONObject.toJSONString(deleteDnatDto));

    }

    private DnatDto convertToDnatDto(VfwInfoDto vfwInfoDto, DeviceFloatingVfwDto deviceFloatingVfwDto) {
        DnatDto createDnatDto = new DnatDto();

        createDnatDto.setIp(vfwInfoDto.getMgrIp());
        createDnatDto.setPassword(vfwInfoDto.getUserPwd());
        createDnatDto.setUsername(vfwInfoDto.getUserName());
        AclDto aclDto = new AclDto();
        aclDto.setAclname(deviceFloatingVfwDto.getAclName());
        aclDto.setVrf(deviceFloatingVfwDto.getVpnInstanceId());
        createDnatDto.setAclDto(aclDto);

        NatStaticDto natStaticDto = new NatStaticDto();
        natStaticDto.setAclname(deviceFloatingVfwDto.getAclName());
        natStaticDto.setEip(deviceFloatingVfwDto.getFixedIpAddress());
        natStaticDto.setIp(deviceFloatingVfwDto.getFloatingIpAddress());
        String interfaceName = floatingIpUtil.getExtIntfName(vfwInfoDto);
        natStaticDto.setInterfaceName(interfaceName);
        natStaticDto.setVrf(deviceFloatingVfwDto.getVpnInstanceId());
        natStaticDto.setExternalVrf("external_vpn");
        createDnatDto.setNatStaticDto(natStaticDto);

        return createDnatDto;
    }


}
