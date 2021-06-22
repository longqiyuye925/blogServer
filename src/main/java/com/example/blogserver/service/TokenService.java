package com.example.blogserver.service;

import com.example.blogserver.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/21
 */
@Service
public class TokenService {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 查看redis里面是否有这个token串
     * 1 没有
     */
    public String verifyToken(String tokenStr) {
        if (null == tokenStr) {
            return "1";
        }
        return redisUtil.getString(tokenStr);
    }


    /**
     * 生成一个新的token串(暂时不使用jwttoken)
     * 这里的规则是随机数_当前时间_账号
     */

    public String createTokenStr(String account) {
        return Math.random() + "_" + System.currentTimeMillis() + "_" + account;
    }

    /**
     * 将token串存入redis
     */

    public void save(String tokenStr) {
        redisUtil.setex(tokenStr, tokenStr);
    }
}
