package com.uca.network.core.port.service;

import com.uca.network.core.mapper.ports.dto.PortsDto;
import com.uca.network.core.port.controller.model.PortsReq;

import java.util.List;

public interface PortsService {

    public void createPort(PortsReq portsReq);

    public void deletePort(String tenantId, String portId);

    public List<PortsDto> queryPortsAttrByNetworkId(String tenantId, String networkId);
}
