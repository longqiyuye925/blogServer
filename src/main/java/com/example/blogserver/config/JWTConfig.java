package com.example.blogserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/25
 */
@Configuration
public class JWTConfig {
    /**
     * 用于OAuth2生成的token和JWT生成的token进行一个转换
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("wsc");//此处的key，待会用来解析时有用
        return jwtAccessTokenConverter;
    }
}
