package com.qklt.chatroom.config;

import com.qklt.chatroom.handler.MarcoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 配置websocket映射
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(marcoHandler(), "/webSocket/{id}");
    }

    @Bean
    public MarcoHandler marcoHandler() {
        return new MarcoHandler();
    }

}