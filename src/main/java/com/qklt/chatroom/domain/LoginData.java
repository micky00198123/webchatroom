package com.qklt.chatroom.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class LoginData {

    @Pattern(regexp = "^[a-zA-Z0-9_]{4,16}$", message = "用户名不合法")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$", message = "密码不合法")
    private String password;

}
