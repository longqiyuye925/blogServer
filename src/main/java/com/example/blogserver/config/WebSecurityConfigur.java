package com.example.blogserver.config;

import com.example.blogserver.handler.MyAccessDeniedHandler;
import com.example.blogserver.handler.MyAuthenticationFailureHandler;
import com.example.blogserver.handler.MyAuthenticationSuccessHandler;
import com.example.blogserver.interceptor.MyAbstractSecurityInterceptor;
import com.example.blogserver.service.CustomizeAuthenticationEntryPoint;
import com.example.blogserver.util.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/16
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigur extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAbstractSecurityInterceptor myAbstractSecurityInterceptor;
    @Autowired
    @Qualifier("myUserDetailsService")
    private UserDetailsService myUserDetailsService;
    @Autowired
    private CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;


    /**
     * 把我们自己实现的UserDetailsService的对象放入auth中
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(myPasswordEncoder);
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()//开启模拟请求
                .authorizeRequests()
                .and()
                .formLogin()
                .permitAll()//登录页面用户任意访问
                .successHandler(myAuthenticationSuccessHandler)//登录成功处理逻辑
                .failureHandler(myAuthenticationFailureHandler)//登录失败处理逻辑
                .and()
                .logout()//登出
                .permitAll()
                .deleteCookies("JSESSIONID")//登出之后删除cookie
                .and()
                .headers()
                .frameOptions().sameOrigin() //注销行为任意访问
                .and().exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);
        //.authenticationEntryPoint(customizeAuthenticationEntryPoint)//未登录提示语
        //把我们自己定义的拦截器放入到HttpSecurity里
        http.addFilterBefore(myAbstractSecurityInterceptor, FilterSecurityInterceptor.class)
        ;
    }
    /**
     * password密码模式需要使用此认证管理器
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
