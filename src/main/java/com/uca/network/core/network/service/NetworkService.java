package com.uca.network.core.network.service;

import com.uca.network.core.network.controller.model.NetworkReq;
import com.uca.network.core.network.vo.NetworkVo;

import java.util.List;

public interface NetworkService {

    public NetworkVo createNetwork(NetworkReq networkReq);

    public List<NetworkVo> queryNetworks(String tenantId);

    public NetworkVo queryNetworkDetail(String tenantId,String networkId);

    public void deleteNetwork(String tenantId,String networkId);

}
