package com.qklt.chatroom.controller;

import com.qklt.chatroom.domain.MessageType;
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
        // 防止多次登录或刷新
        if(ifOffline.contains(username) || users.containsKey(username)){
            ifOffline.remove(username);
        } else {
            String message = packingMsg(MessageType.ONLINE, username, "");
            broadcast(message);
        }
        users.put(username, session);
        mySelf = username;
        String message = packingMsg(MessageType.MEMBER, username,users.keySet().toString());
        session.getAsyncRemote().sendText(message);
//        System.out.println("当前在线人数："+users.size());
    }

    @OnClose
    public void OnClose(@PathParam("username")String username){
        if(users.remove(username) != null){
            readyOffline(username);
        }
    }
 
    @OnMessage
    public void onMessage(@PathParam("username")String username, String message){
        if(users.containsKey(username)){
            message = packingMsg(MessageType.MESSAGE, username, message);
            broadcast(message);
        }
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

    /**
     * 消息包装
     * @param type
     * @param sender
     * @param word
     * @return
     */
    private String packingMsg(MessageType type, String sender,  String word){
        String online = "ONLINE@";
        String offline = "OFFLINE@";
        String message = "MESSAGE@";
        String member = "MEMBER@";
        switch (type){
            case ONLINE:
                word = online + sender;
                break;
            case OFFLINE:
                word = offline + sender;
                break;
            case MESSAGE:
                word = message + sender + ":" + word;
                break;
            case MEMBER:
                word = member + word;
        }
        return word;
    }

    private void readyOffline(String username){
        new Thread(()->{
            ifOffline.add(username);
            for(int i = 0; i < 10; i++){
                try {
                    Thread.sleep(300);
                    // 10s内再次上线即不发广播
                    if(!ifOffline.contains(username)){
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ifOffline.remove(username);
            String message = packingMsg(MessageType.OFFLINE, username, "");
            broadcast(message);
//            System.out.println("当前在线人数："+users.size());
        }).start();
    }


}