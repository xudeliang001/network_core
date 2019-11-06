package com.uca.network.extension.allocations.service;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/5 15:32
 */
public interface MacAddressService {

    String getMacAddress();

    /**
     * 获取指定来源的mac地址
     */
    String getMacAddress(String macAddress, String macSource);

    /**
     * 删除mac地址
     */
    void deleteMacAddress(String macAddress);
}
