package com.uca.network.common.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Value("${network.dns}")
    private String dns ;

    @Value("${network.swaggerEnable}")
    private String swaggerEnable;

    @Value("${network.ipObligateHigh}")
    private String ipObligateHigh;

    @Value("${network.ipObligateLow}")
    private String ipObligateLow;

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getSwaggerEnable() {
        return swaggerEnable;
    }

    public void setSwaggerEnable(String swaggerEnable) {
        this.swaggerEnable = swaggerEnable;
    }

    public String getIpObligateHigh() {
        return ipObligateHigh;
    }

    public void setIpObligateHigh(String ipObligateHigh) {
        this.ipObligateHigh = ipObligateHigh;
    }

    public String getIpObligateLow() {
        return ipObligateLow;
    }

    public void setIpObligateLow(String ipObligateLow) {
        this.ipObligateLow = ipObligateLow;
    }
}
