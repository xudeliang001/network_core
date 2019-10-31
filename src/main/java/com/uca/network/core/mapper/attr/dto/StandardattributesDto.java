package com.uca.network.core.mapper.attr.dto;

import java.util.Date;

public class StandardattributesDto {

    private String id;

    private String description;

    private String resourceType;

    private Date updateTime;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "StandardattributesDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", resourceType='" + resourceType + '\'' +
                '}';
    }
}
