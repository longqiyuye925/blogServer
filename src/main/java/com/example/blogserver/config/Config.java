package com.example.blogserver.config;

import com.example.blogserver.interceptor.TokenInterCeptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/15
 */
@Configuration
public class Config implements WebMvcConfigurer {
    @Autowired
    TokenInterCeptor tokenInterCeptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterCeptor).addPathPatterns("/customer/*");
    }

}
