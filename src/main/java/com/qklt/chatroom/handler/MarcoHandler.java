package com.qklt.chatroom.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 *
 */
public class MarcoHandler extends AbstractWebSocketHandler {

    /**
     * 处理文本类型消息
     * @param session
     * @param message
     * @throws Exception
     */
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: " + message.getPayload());
        session.sendMessage(new TextMessage("Polo!"));
    }

    /**
     * 新连接建立后调用
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("WebSocket连接成功");
//        this.handleTextMessage();
    }

    /**
     * 连接关闭后调用
     * @param session
     * @param status
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Connection closed. Status: " + status);
    }

}