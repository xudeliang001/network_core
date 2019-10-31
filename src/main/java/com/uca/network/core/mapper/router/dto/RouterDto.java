package com.uca.network.core.mapper.router.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by g15861 on 2017/12/21.
 *
 * @author g15861
 */
public class RouterDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;


    private String status;


    private String name;

    private Boolean adminStateUp;

    private String tenantId;

    @JSONField(serialize=false)
    private String standardAttrId;

    private String description;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setAdminStateUp(Boolean adminStateUp) {
        this.adminStateUp = adminStateUp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAdminStateUp() {
        return adminStateUp;
    }

    public String getStandardAttrId() {
        return standardAttrId;
    }

    public void setStandardAttrId(String standardAttrId) {
        this.standardAttrId = standardAttrId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
