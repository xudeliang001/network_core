package com.uca.network.core.mapper.attr.dto;

public class StandardattributesDto {

    private long id;

    private String description;

    private String resourceType;

    private String updateTime;

    private String createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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
