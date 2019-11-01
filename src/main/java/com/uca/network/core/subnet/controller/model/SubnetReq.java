package com.uca.network.core.subnet.controller.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class SubnetReq {
    private String id;

    @JsonProperty("tenant_id")
    @JSONField(name = "tenant_id")
    private String tenantId;

    private String name;

    @JsonProperty("network_id")
    @JSONField(name = "network_id")
    private String networkId;

    private String cidr;

    @JsonProperty("gateway_ip")
    @JSONField(name = "gateway_ip")
    private String gatewayIp;

    @JsonProperty("ip_version")
    private int ipVersion;

    /**
     * startIp  lastIp
     */
    @JSONField(name = "allocation_pools")
    private Map<String, Object> ipPools;

    @JSONField(name = "dns_nameservers")
    private List<String> dns;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    public int getIpVersion() {
        return ipVersion;
    }

    public void setIpVersion(int ipVersion) {
        this.ipVersion = ipVersion;
    }

    public List<String> getDns() {
        return dns;
    }

    public void setDns(List<String> dns) {
        this.dns = dns;
    }

    public Map<String, Object> getIpPools() {
        return ipPools;
    }

    public void setIpPools(Map<String, Object> ipPools) {
        this.ipPools = ipPools;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
