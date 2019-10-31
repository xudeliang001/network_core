package com.uca.network.core.mapper.network.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class NetworkDtoWrapper implements Serializable {

    @JsonProperty("data")
    private List<NetworkDto> networkDtos;

    public List<NetworkDto> getNetworkDtos() {
        return networkDtos;
    }

    public void setNetworkDtos(List<NetworkDto> networkDtos) {
        this.networkDtos = networkDtos;
    }
}
