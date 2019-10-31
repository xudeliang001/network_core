package com.uca.network.core.mapper.router.dto;

import java.io.Serializable;
import java.util.List;

public class RouterDtoWrapper implements Serializable {
    private List<RouterDto> routers;

    public List<RouterDto> getRouters() {
        return routers;
    }

    public void setRouters(List<RouterDto> routers) {
        this.routers = routers;
    }
}
