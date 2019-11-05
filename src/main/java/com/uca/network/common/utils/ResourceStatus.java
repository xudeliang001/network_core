package com.uca.network.common.utils;

public enum ResourceStatus {
    INACTIVE("INACTIVE"), ACTIVE("ACTIVE"), PENDING_UPDATE("PENDING_UPDATE"),DOWN("DOWN");

    private String val;

    ResourceStatus(String val) {

        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
