package com.example.blogserver.handler;

import com.alibaba.fastjson.JSON;
import com.example.blogserver.entity.ControlAccount;
import com.example.blogserver.entity.Response;
import com.example.blogserver.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/18
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    ControlAccount controlAccount;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        //登录成功后清除掉锁定的次数
        String username = httpServletRequest.getParameter("username");
        controlAccount.getLockTable().remove(username);
        //自己编码的JWTtoken
        String token = JWTUtil.createToken("account");
        Response response = new Response();
        response.setStatus("666");
        response.setMsg("登录成功");
        response.setToken(token);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(response));
    }
}
