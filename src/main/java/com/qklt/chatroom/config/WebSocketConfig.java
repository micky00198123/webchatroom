package com.qklt.chatroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 注册webSocketEndpoint
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter confServerEndpointExporter(){
        return new ServerEndpointExporter();
    }

}