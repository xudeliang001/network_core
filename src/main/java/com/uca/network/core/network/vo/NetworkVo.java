package com.uca.network.core.network.vo;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkVo {

    private String id;

    private String name;

    @JSONField(name = "admin_state_up")
    @JsonProperty("admin_state_up")
    private Boolean adminStateUp;

    private String status;

    @JsonProperty("tenant_id")
    @JSONField(name = "tenant_id")
    private String tenantId;

    @JSONField(name = "standard_attr_id")
    @JsonProperty("standard_attr_id")
    private String standardAttrId;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdminStateUp() {
        return adminStateUp;
    }

    public void setAdminStateUp(Boolean adminStateUp) {
        this.adminStateUp = adminStateUp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }


    public String getStandardAttrId() {
        return standardAttrId;
    }

    public void setStandardAttrId(String standardAttrId) {
        this.standardAttrId = standardAttrId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
