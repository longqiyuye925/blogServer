package com.example.blogserver.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.example.blogserver.entity.Response;
import com.example.blogserver.util.JWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/15
 */
@Component
public class TokenInterCeptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String logintoken = request.getHeader("logintoken");
        if(!JWTUtil.verify(logintoken)){
            Response myresponse = new Response();
            myresponse.setMsg("token is fail");
            myresponse.setStatus(Response.FAIL);
            response.getWriter().write(JSONObject.toJSONString(myresponse));
            return false;
        }
        return true;
    }
}
