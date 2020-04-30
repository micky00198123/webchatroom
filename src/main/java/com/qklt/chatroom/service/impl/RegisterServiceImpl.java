package com.qklt.chatroom.service.impl;


import com.qklt.chatroom.dao.impl.UserDAOImpl;
import com.qklt.chatroom.domain.LoginData;
import com.qklt.chatroom.domain.User;
import com.qklt.chatroom.service.RegisterService;
import com.qklt.chatroom.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class RegisterServiceImpl implements RegisterService {

    private UserDAOImpl ud;

    @Autowired
    public RegisterServiceImpl(UserDAOImpl ud) {
        this.ud = ud;
    }

    @Transactional(rollbackFor={Exception.class })
    @Override
    public boolean registerAccount(LoginData ld) {

        User user = new User();
        user.setUserName(ld.getUsername());
        ld.setPassword(ServiceUtils.getMD5String(ld.getPassword()));

        try {
            // 两个数据表必须同时写入,防止意外中断产生脏数据
            ud.insertUserToAccount(ld);
            ud.insertUserToInformation(user);
        } catch (Exception e) {
            e.printStackTrace();
            // 事务回滚后返回指定值
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }

    @Override
    public boolean findUserByName(String name) {
        return ud.checkDuplicateNames(name);
    }
}
