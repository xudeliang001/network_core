package com.uca.network.extension.allocations.dto;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/4 13:33
 */
public class IpAllocationsDto {

    private String ipAddress;

    private String macAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
