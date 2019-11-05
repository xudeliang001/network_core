package com.uca.network.extension.plugin.floatingip.util;

import com.uca.network.extension.plugin.floatingip.dto.VfwInfoDto;
import org.springframework.stereotype.Service;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/4
 */
@Service
public class FloatingIpUtil {

    /**
     * 根据私网IP查询所属的vpn实例Id
     * @param fixedIpAddress
     * @return
     */
    public String getVpnInstanceIdByFixedIpAddr(String fixedIpAddress){

        /**
         * 根据主机的私网IP，查询ipallocations表,得到对应的subnet_id，
         * 根据subnetId查询subnets表得到对应的cidr，
         * 然后根据cidr查询l3_cidr表得到l3vni，
         * 根据l3vni查询vpn_instance表，得到vpn_instance_id
         */

        return null;
    }


    /**
     * 根据vpn实例Id查询所在虚墙相信信息
     * @param vpnInstanceId
     * @return
     */
    public VfwInfoDto getVfwInfo(String vpnInstanceId){

        /**
         * 据vpn_instance_id查询device_vfirewall表得到虚墙的context_id，
         * 然后根据context_id查询contexts表得到虚墙的设备具体信息，包括mgr_ip、username、userpwd等
         */

        return null;
    }


    /**
     * 根据虚墙信息查询 出接口的name
     * @param vfwInfoDto
     * @return
     */
    public String getExtIntfName(VfwInfoDto vfwInfoDto){
        /**
         * 根据device_id查询device_fw_base表得到device_fw_base_id，
         * 根据	device_fw_base_id查询device_fw_base_interface表得到上行接口的名字upstream_interface_name
         */

        return null;
    }

}
