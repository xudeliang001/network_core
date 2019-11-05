package com.uca.network.extension.plugin.floatingip.agent.client;

import com.uca.network.common.utils.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuyong 00350
 * @version 1.0
 * @date 2019/11/4
 */
@Service
public class VfwClient {

    @Autowired
    private RestClient restClient;

    /**
     * @param agentServer
     * @param createDnatDto
     */
    public void createDnat(String agentServer, String createDnatDto){
        String url = agentServer + "/eip/creatednat";
        restClient.putWithToken(url, null, createDnatDto, String.class);
    }


    /**
     * @param agentServer
     * @param deleteDnatDto
     */
    public void deleteDnat(String agentServer, String deleteDnatDto){
        String url = agentServer + "/eip/deletednat";
        restClient.putWithToken(url, null, deleteDnatDto, String.class);

    }
}
