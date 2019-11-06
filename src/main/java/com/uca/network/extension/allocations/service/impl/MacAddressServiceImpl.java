package com.uca.network.extension.allocations.service.impl;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.uca.network.common.exception.ErrorCode;
import com.uca.network.common.exception.AllocationsException;
import com.uca.network.extension.allocations.service.MacAddressService;
import com.uca.network.extension.mapper.allocations.MacAddressMapper;
import com.uca.network.extension.mapper.allocations.model.MacAddress;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/5 15:33
 */
@Lazy
@Service
public class MacAddressServiceImpl implements MacAddressService {

    @Value("${network.macAddressTemplate}")
    private String macAddressTemplate;

    private Logger logger = LoggerFactory.getLogger(MacAddressServiceImpl.class);

    @Autowired
    private MacAddressMapper macAddressMapper;

    @Override
    public String getMacAddress() {
        while (true) {
            String[] attr = macAddressTemplate.split(":");
            for (int i = 0; i < attr.length; i++) {
                if (StringUtils.equals("00", attr[i])) {
                    attr[i] = randomHexString(2);
                }
            }
            String macAddress = Joiner.on(":").join(attr);
            try {
                return getMacAddress(macAddress, "template-generator");
            } catch (Exception e) {
                logger.error("create mac address {} error", macAddress);
            }
        }
    }

    @Override
    public String getMacAddress(String macAddress, String macSource) {
        MacAddress ma = new MacAddress();
        ma.setMacAddress(macAddress);
        ma.setMacSource(macSource);
        try {
            macAddressMapper.insert(ma);
        } catch (Exception e) {
            logger.error("create mac address {} error", macAddress);
            throw new AllocationsException(ErrorCode.MAC_ADDRESS_EXPLICIT);
        }
        return macAddress;
    }

    @Override
    public void deleteMacAddress(String macAddress) {
        macAddressMapper.delete(macAddress);
    }

    /**
     * 获取16进制随机数
     * @param len
     * @return
     * @throws
     */
    private String randomHexString(int len) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < len; i++) {
            result.append(Integer.toHexString(ThreadLocalRandom.current().nextInt(16)));
        }
        return result.toString();
    }
}
