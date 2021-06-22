package com.example.blogserver.controller;

import com.example.blogserver.entity.Customer;

import com.example.blogserver.entity.Response;
import com.example.blogserver.util.JWTUtil;
import com.example.blogserver.util.RedisUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/15
 */
@RestController
@RequestMapping("/customer")
public class LoginController {
    @Autowired
    Response response;
    @Autowired
    RedisUtil redisUtil;
    Log log = LogFactory.getLog(LoginController.class);

    @RequestMapping("/userValidate")
    public Response userValidate(@RequestBody Customer customer) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String logintoken = servletRequestAttributes.getRequest().getHeader("logintoken");
        //校验用户
        Boolean account = volidateUser(customer, logintoken);

        return response;
    }
    @RequestMapping("/query")
    public Response userQuery(@RequestBody Customer customer) {
        String tokenStr = JWTUtil.createToken("wsc");
        System.out.println("tokenStr" + tokenStr);
        redisUtil.setex("jinan0621",tokenStr);

        response.setMsg("查询成功");
        response.setStatus(Response.SUCCESS);
        return response;
    }
    /**
     * 校验用户密码
     *
     * @param customer
     * @return
     */
    private boolean volidateUser(Customer customer, String logintoken) {
        if (null == logintoken || 0 == logintoken.length()) {
            //token为空的时候才校验账号密码
            log.info(customer.getUsername() + "|" + customer.getPassword());
            if (null == customer.getUsername() || 0 == customer.getUsername().length()) {
                response.setMsg("失败");
                response.setStatus(Response.FAIL);
                return false;

            }
        }
        response.setMsg("成功");
        response.setStatus(Response.SUCCESS);
        return true;
    }
}
