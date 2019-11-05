package com.uca.network.common.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ServerConfig {

    @Value("${network.dns}")
    private String dns ;

    @Value("${network.swaggerEnable}")
    private String swaggerEnable;

    @Value("${network.ipObligateHigh}")
    private String ipObligateHigh;

    @Value("${network.ipObligateLow}")
    private String ipObligateLow;

    @Value("${network.extensionAgent}")
    private String extensionAgent;

}
