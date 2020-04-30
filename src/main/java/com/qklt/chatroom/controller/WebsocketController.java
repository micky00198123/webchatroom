package com.qklt.chatroom.controller;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/webSocket/{username}")
@Component
public class WebsocketController {

    // 用static存放用户可在多个实例间共享
    private static Map<String, Session> users = new HashMap<>();
    // 准备下线
    private static List<String> ifOffline = new ArrayList<>();
    // 我自己
    private String mySelf = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("username")String username){
        if(!users.containsKey(username)){
            broadcast(username+"上线，当前在线"+(users.size()+1)+"人");
        }
        ifOffline.remove(username);
        users.put(username, session);
        mySelf = username;
        System.out.println("当前在线人数："+users.size());
    }

    @OnClose
    public void OnClose(@PathParam("username")String username){
        ifOffline.add(username);
        readyOffline(username);
    }
 
    @OnMessage
    public void onMessage(@PathParam("username")String username, String message){
        broadcast(username + "：" + message);
    }

    /**
     * 对除自己和即将下线以外的所有人广播
     * @param message
     */
    private void broadcast(String message){
        for(String username : users.keySet()){
            if(ifOffline.contains(username) || mySelf.equals(username)){
                continue;
            }
            users.get(username).getAsyncRemote().sendText(message);
        }
    }

    private void readyOffline(String username){
        new Thread(()->{
            for(int i = 0; i < 10; i++){
                try {
                    Thread.sleep(1000);
                    // 10s内再次上线即不发广播
                    if(!ifOffline.contains(username)){
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            users.remove(username);
            ifOffline.remove(username);
            broadcast(username+"下线，当前在线"+(users.size())+"人");
            System.out.println("当前在线人数："+users.size());
        }).start();
    }


}