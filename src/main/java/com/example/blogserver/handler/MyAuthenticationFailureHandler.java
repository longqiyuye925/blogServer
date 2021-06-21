package com.example.blogserver.handler;

import com.alibaba.fastjson.JSON;
import com.example.blogserver.entity.ControlAccount;
import com.example.blogserver.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

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
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    ControlAccount controlAccount;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Response response = new Response();

        if (e instanceof AccountExpiredException) {
            //账号过期
            response.setStatus("fail001");
            response.setMsg("账号过期");
        } else if (e instanceof BadCredentialsException) {
            String username = httpServletRequest.getParameter("username");
            Integer num = controlAccount.getLockTable().get(username);
            if (null != num) {
                controlAccount.putLockAccount(username, new Integer(num.intValue() + 1));
            } else {
                controlAccount.putLockAccount(username, new Integer(1));
            }
            //密码错误
            response.setStatus("fail002");
            response.setMsg("密码错误");
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            response.setStatus("fail003");
            response.setMsg("密码过期");
        } else if (e instanceof DisabledException) {
            //账号不可用
            response.setStatus("fail004");
            response.setMsg("账号不可用");
        } else if (e instanceof LockedException) {
            //账号锁定
            response.setStatus("fail005");
            response.setMsg("账号锁定");
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            response.setStatus("fail006");
            response.setMsg("用户不存在");
        } else {
            //其他错误
            response.setStatus("fail000");
            response.setMsg("其他错误");
        }

        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(response));
    }
}
