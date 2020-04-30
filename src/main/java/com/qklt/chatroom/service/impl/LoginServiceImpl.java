package com.qklt.chatroom.service.impl;

import com.qklt.chatroom.dao.impl.UserDAOImpl;
import com.qklt.chatroom.domain.LoginData;
import com.qklt.chatroom.domain.User;
import com.qklt.chatroom.service.LoginService;
import com.qklt.chatroom.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private UserDAOImpl ud;

    @Autowired
    public LoginServiceImpl(UserDAOImpl ud) {
        this.ud = ud;
    }

    @Override
    public User checkAccount(LoginData ld) {
        ld.setPassword(ServiceUtils.getMD5String(ld.getPassword()));
        return ud.checkAccount(ld) ?
                ud.getUserByName(ld.getUsername()) : null;
    }
}
