package com.example.blogserver.config;

import com.example.blogserver.handler.MyAccessDeniedHandler;
import com.example.blogserver.handler.MyAuthenticationFailureHandler;
import com.example.blogserver.handler.MyAuthenticationSuccessHandler;
import com.example.blogserver.interceptor.MyAbstractSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/25
 */
@Configuration
@EnableResourceServer
public class MyResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private MyAbstractSecurityInterceptor myAbstractSecurityInterceptor;
    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin() // 表单登录
                .loginProcessingUrl("/login") // 处理表单登录 URL 注意这里即使是使用默认的登录页面也一定要指定，否则资源服务器会找不到路径报404
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**", "/login", "/login/**", "logout/**")
                .permitAll()
                .anyRequest()
                .authenticated()
        ;
        http.addFilterBefore(myAbstractSecurityInterceptor, FilterSecurityInterceptor.class);
    }
}
