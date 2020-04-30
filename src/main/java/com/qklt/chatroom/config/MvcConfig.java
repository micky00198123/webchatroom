package com.qklt.chatroom.config;


import com.qklt.chatroom.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/html/login")
                .setViewName("/html/login.html");
        registry.addViewController("/html/register")
                .setViewName("/html/register.html");
        registry.addViewController("/html/hello")
                .setViewName("/html/hello.html");
        registry.addViewController("/html/home")
                .setViewName("/html/home.html");
    }

    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/html/welcome");
    }

}
