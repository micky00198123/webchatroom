package com.qklt.chatroom.controller;


import com.qklt.chatroom.domain.LoginData;
import com.qklt.chatroom.domain.User;
import com.qklt.chatroom.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService ls) {
        this.loginService = ls;
    }

    @RequestMapping("/bai")
    public String toBaidu(){
        return "redirect:https://www.baidu.com/";
    }

    @PostMapping(value = "/user/login")
    public String checkAccount(LoginData ld, BindingResult br,
                               RedirectAttributes attr,
                               HttpSession httpSession) {

        if(br.hasErrors()) {
            attr.addFlashAttribute("msg",
                    br.getFieldError().getDefaultMessage());
            return "redirect:/html/login";
        }

        User user = loginService.checkAccount(ld);
        if(user != null) {
            httpSession.setAttribute("user", user);
            return "redirect:/html/hello";
        } else {
            attr.addFlashAttribute("msg", "用户名或密码错误");
            return "redirect:/html/login";
        }
        
    }

}
