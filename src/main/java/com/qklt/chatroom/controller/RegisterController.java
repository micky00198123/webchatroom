package com.qklt.chatroom.controller;


import com.qklt.chatroom.domain.LoginData;
import com.qklt.chatroom.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegisterController {

    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping(value = "/user/register")
    public String register(RedirectAttributes attr, @Valid LoginData loginData) {

        if(registerService.findUserByName(loginData.getUsername())) {
            attr.addFlashAttribute("msg", "<font color='red'>用户名已使用!</font>");
            return "redirect:/html/register";
        }

        if (registerService.registerAccount(loginData)) {
            attr.addFlashAttribute("msg", "<font color='green'>注册成功</font>");
            return "redirect:/html/login";
        } else {
            attr.addFlashAttribute("msg", "<font color='red'>服务器错误,注册失败</font>");
            return "redirect:/html/register";
        }

    }

    @GetMapping(value = "/user/register")
    @ResponseBody
    public String findName(String username) {
        if(registerService.findUserByName(username)) {
            return "<font color='red'>用户名已使用</font>";
        } else {
            return "<font color='green'>用户名可用</font>";
        }
    }


}