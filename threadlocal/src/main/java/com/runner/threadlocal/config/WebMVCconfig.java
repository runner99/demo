package com.runner.threadlocal.config;

import com.runner.threadlocal.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCconfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        //跨域配置
        registry.addMapping("/**").allowedOrigins("https://blog.mszlu.com","http://blog1.mszlu.com","http://localhost:8080");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**").excludePathPatterns("/login");
    }
}
