package com.qklt.chatroom.service;

import com.qklt.chatroom.domain.LoginData;
import com.qklt.chatroom.domain.User;

public interface LoginService {

    /**
     * 检查是否登录成功
     * @param ld
     * @return
     */
    public User checkAccount(LoginData ld);

}
