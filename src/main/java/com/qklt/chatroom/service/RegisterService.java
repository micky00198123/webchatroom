package com.qklt.chatroom.service;


import com.qklt.chatroom.domain.LoginData;

public interface RegisterService {

    /**
     * 账号写入数据库,并检查是否注册成功
     * @param ld
     * @return
     */
    public boolean registerAccount(LoginData ld);

    /**
     * 使用用户名查找用户
     * @param name
     * @return
     */
    public boolean findUserByName(String name);

}
